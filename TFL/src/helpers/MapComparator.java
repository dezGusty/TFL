package helpers;

import java.util.Comparator;

/**
 * @author Paula
 *
 */
public class MapComparator implements Comparator<Double> {

	private double value;

	/**
	 * @param value
	 */
	public MapComparator(double value) {
		this.value = value;
	}

	@Override
	public int compare(Double arg0, Double arg1) {
		if (Math.abs((value - arg0)) < Math.abs((value - arg1))) {
			return -1;
		}
		if (Math.abs((value - arg0)) > Math.abs((value - arg1))) {
			return 1;
		}
		return 0;
	}
}