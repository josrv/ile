package com.josrv.ile.gui

import kotlin.properties.Delegates.notNull

class DraggingResizer(
    private val numPanels: Int,
    widthSupplier: () -> Double,
    private val margin: Int = DEFAULT_MARGIN
) {
    private val width by lazy(widthSupplier)

    // Local state
    private var dragging = false
    private val ratios = MutableList(numPanels) { 1.0 / numPanels }
    private lateinit var panelPositions: List<Double>
    private var leftPanelIndex by notNull<Int>()
    private var leftStart by notNull<Double>()
    private var totalWidth by notNull<Double>()
    private var totalRatio by notNull<Double>()

    fun startDrag(x: Double) {
        updateColumnPositions()

        val (panelIndex) = panelPositions
            .asSequence()
            .mapIndexed { index, d -> index to d }
            .find { (_, pos) ->
                x > pos - margin && x < pos + margin
            } ?: return

        if (panelIndex == 0 || panelIndex == numPanels) {
            return
        }

        leftPanelIndex = panelIndex - 1
        leftStart = panelPositions[leftPanelIndex]
        totalWidth = panelPositions[panelIndex + 1] - panelPositions[leftPanelIndex]
        totalRatio = ratios[panelIndex - 1] + ratios[panelIndex]
        dragging = true
    }

    fun stopDrag() {
        dragging = false
    }

    fun onDrag(x: Double, callback: (Pair<Int, Double>, Pair<Int, Double>) -> Unit) {
        if (dragging) {
            if (x > leftStart && x < leftStart + totalWidth) {
                val rightPanel = leftPanelIndex + 1
                val newRatio = totalRatio * (x - leftStart) / totalWidth
                ratios[leftPanelIndex] = newRatio
                ratios[rightPanel] = totalRatio - newRatio

                callback(leftPanelIndex to ratios[leftPanelIndex], rightPanel to ratios[rightPanel])
            }
        }
    }

    private fun updateColumnPositions() {
        panelPositions = (listOf(0.0) + ratios).foldIndexed(mutableListOf<Double>()) { i, acc, d ->
            acc.add(d + (if (i == 0) 0.0 else acc[i - 1]))

            acc
        }.map { it * width }
    }

    companion object {
        const val DEFAULT_MARGIN = 30
    }
}