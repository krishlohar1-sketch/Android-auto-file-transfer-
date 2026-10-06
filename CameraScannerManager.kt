package com.example.autofiletransfer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class CameraScannerManager(private val onTokenDetected: (String) -> Unit) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient()

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val rawValue = barcode.rawValue
                        if (rawValue != null && rawValue.startsWith("TRANSFER_ID:")) {
                            // Extract target MAC address from visual handshake token
                            val macAddress = rawValue.replace("TRANSFER_ID:", "")
                            onTokenDetected(macAddress)
                            break
                        }
                    }
                }
                .addOnFailureListener {
                    // Handle barcode detection failure smoothly
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}
