/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package larson.pm.chart;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Budget demo pie chart.
 */
public class BudgetPieChart extends AbstractDemoChart {
	private int[] colors;
	private double[] values;
	/**
	 * 每个条目的名称
	 */
	private String[] itemNames;
	
	/**
	 * 设置每个条目的名称
	 */
	public void setItemNames(String[] itemNames){
		this.itemNames = itemNames;
	}
	
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Budget chart";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The budget per project for this year (pie chart)";
  }
  
  /**
   * 设置颜色和值
   * @param colors
   * @param values
   */
  public void setColorAndValues(int[] colors,double[] values){
	  this.colors = colors;
	  this.values = values;
  }

  /**
   * Executes the chart demo.
   * @param context the context
   * @return the built intent
   */
  public Intent execute(Context context) {
    
    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitleTextSize(20);
    renderer.setDisplayValues(true);
    renderer.setShowLabels(true);
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setGradientEnabled(true);
    r.setGradientStart(0, Color.BLUE);
    r.setGradientStop(0, Color.GREEN);
    r.setHighlighted(true);
    Intent intent = ChartFactory.getPieChartIntent(context,
        buildDataset("Project budget", values), renderer, "Budget");
    return intent;
  }
  
  private CategorySeries buildDataset(String title, double[] values) {
	    CategorySeries series = new CategorySeries(title);
	    for(int i=0;i<values.length;i++)
	      series.add(itemNames[i], values[i]);
	    return series;
	  }

}
