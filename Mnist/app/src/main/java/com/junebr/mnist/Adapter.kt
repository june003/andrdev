package com.junebr.mnist

import android.graphics.Bitmap
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.content_main.view.*


//  RecyclerView, Adapter, and ViewHolder pattern

class ClassifierAdapter(listOfClassifiers: List<MnistClassifier>) : RecyclerView.Adapter<ClassifierViewHolder>() {

    private val classifiers: List<ClassifierViewModel> = listOfClassifiers.map { ClassifierViewModel(it.name, 0, it) }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ClassifierViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.content_main, parent, false)
        return ClassifierViewHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: ClassifierViewHolder, position: Int) {
        val vm = classifiers[position]
        holder.let {
            it.classifierName.text = vm.name
            it.classifierResult.text = vm.result.toString()
        }
    }

    override fun getItemCount(): Int = classifiers.size

    fun classify(bitmap: Bitmap) {
        classifiers.forEachIndexed { index, classifier ->
            classifier.classify(bitmap)
            notifyItemChanged(index)
        }
    }
}


class ClassifierViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var classifierName: TextView
    var classifierResult: TextView

    init {
        classifierName = itemView.classifier_name
        classifierResult = itemView.classifier_result
    }
}

data class ClassifierViewModel(val name: String, var result: Int = 0, val mnistClassifier: MnistClassifier) {
    fun classify(bitmap: Bitmap) {
        result = mnistClassifier.classify(bitmap)
    }
}