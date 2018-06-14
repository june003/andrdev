package com.junebr.mnist

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout

import kotlinx.android.synthetic.main.activity_main.*
import org.tensorflow.contrib.android.TensorFlowInferenceInterface

class MainActivity : AppCompatActivity() {
    private val INPUT_SIZE = 28

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // setup classifiers
        val outputNodeDef = NodeDef("output", FloatArray(10).toTypedArray())

        val listOfClassifiers = listOf(
                MnistClassifier("v1",
                        TensorFlowInferenceInterface(assets, "model_graph_28_v1.pb"),
                        NodeDef("inputImage", intArrayOf(1, 28, 28, 1).toTypedArray()), outputNodeDef),

                MnistClassifier("valid_graph",
                        TensorFlowInferenceInterface(assets, "valid_graph.pb"),
                        NodeDef("input", intArrayOf(1, 784).toTypedArray()), outputNodeDef),

                MnistClassifier("test",
                        TensorFlowInferenceInterface(assets, "model_graph_28_test.pb"),
                        NodeDef("inputImage", intArrayOf(1, 28, 28, 1).toTypedArray()), outputNodeDef))

        // initialise draw view
        val model = CanvasModel(INPUT_SIZE, INPUT_SIZE)
        val drawableImage = view_draw as DrawableImage
        drawableImage.init(model)

        // setup the adapter
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val adapter = ClassifierAdapter(listOfClassifiers)
        recycler_view.adapter = adapter

        button_clear.setOnClickListener {
            drawableImage.clear()
        }
        button_detect.setOnClickListener {
            val bitmap = drawableImage.getBitmap()
            adapter.classify(bitmap)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
