package com.aws.samples.djlspringboot;

import ai.djl.inference.Predictor;
import ai.djl.modality.cv.DetectedObjects;
import ai.djl.modality.cv.ImageVisualization;
import ai.djl.translate.TranslateException;
import com.aws.samples.djlspringboot.model.InferenceResponse;
import com.aws.samples.djlspringboot.model.InferredObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
public class InferencePointController {

    private static final Logger LOG = LoggerFactory.getLogger(InferencePointController.class);

    @Resource
    private Supplier<Predictor<BufferedImage, DetectedObjects>> predictorSupplier;

    @Resource
	private S3ImageUploader uploader;

    @Resource
	private S3ImageDownloader downloader;

    @GetMapping
    @RequestMapping("/inference")
    public InferenceResponse detect(@RequestParam(name = "file", required = true) String fileName,
                                    @RequestParam(name = "generateOutputImage") Optional<Boolean> generateOutputImage)
			throws IOException, TranslateException {
        BufferedImage img = downloader.download(fileName);
        var inferredObjects = new LinkedList<InferredObject>();
        var outputReference = "";

        try(var p = predictorSupplier.get()) {
            var detected = p.predict(img);
            if(generateOutputImage.orElse(true)) {
                BufferedImage newImage = createImage(detected, img);
                outputReference = uploader.upload(newImage, fileName + ".png");
            }
            detected.items().forEach(e -> inferredObjects.add(new InferredObject(e.getClassName(), e.getProbability())));
        }

        return new InferenceResponse(inferredObjects, outputReference);
    }

    private static BufferedImage createImage(DetectedObjects detection, BufferedImage original) {
		BufferedImage newImage =
				new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(original, 0, 0, null);
		g.dispose();
		ImageVisualization.drawBoundingBoxes(newImage, detection);
		return newImage;
	}

}
