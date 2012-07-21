package com.ProjectDragoon.util;

import javax.swing.JComponent;
import javax.swing.RepaintManager;

/**
 * An extended RepaintManager to allow Swing component integration with Active Rendering by preventing Java from doing any automatic repainting
 * Knowledge of doing this came from here: http://jamesgames.org/resources/double_buffer/double_buffering_and_active_rendering.html#OverridingRepaintManager
 *
 */
public class NoRepaintManager extends RepaintManager {

	public void addDirtyRegion(JComponent c, int x, int y, int w, int h){}
	public void addInvalidComponent(JComponent invalidComponent){}
	public void markCompletelyDirty(JComponent aComponent){}
	public void paintDirtyRegions(){}
}
