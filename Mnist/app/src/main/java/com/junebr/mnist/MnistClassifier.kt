package com.junebr.mnist

import android.graphics.*
import org.tensorflow.contrib.android.TensorFlowInferenceInterface

data class NodeDef<T>(val name: String, val shape: Array<T>)

class MnistClassifier(
        val name: String,
        private var tfInference: TensorFlowInferenceInterface,
        private val inputNodeDef: NodeDef<Int>,
        private val outputNodeDef: NodeDef<Float>) {

    fun classify(bitmap: Bitmap): Int {
        // transform the bitmap to a 784 float array (how our model was trained)
        val inputAsFloatArray = getBitmapAsFloatArray(bitmap)
        // classify! we get a 'one-hot vector' back
        val modelOutputAsFloatArray = classifyModelInput(inputAsFloatArray)
        // return the one that it guessed (one hot vector = [0, 0, 1, 0, 0 ...])
        return modelOutputAsFloatArray.indexOfFirst { it > 0.0 }
    }

    private fun getBitmapAsFloatArray(bitmap: Bitmap): FloatArray {
        val width = bitmap.width
        val height = bitmap.height

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        //bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        return pixels.map {
            // Set 0 for white and 255 for black pixel
            0xff - (it and 0xff)
        }.map {
            it.toFloat()
        }.toFloatArray()
    }

    private fun classifyModelInput(modelInput: FloatArray): FloatArray {
        // feed
        tfInference.feed(inputNodeDef.name, modelInput, *reshape(inputNodeDef.shape.toIntArray()))

        // fetch
        val floatOutputs = outputNodeDef.shape.toFloatArray()
        tfInference.run(arrayOf(outputNodeDef.name))
        tfInference.fetch("output", floatOutputs)
        return floatOutputs
    }

    private fun reshape(intArray: IntArray) = intArray.map { it.toLong() }.toLongArray()
}