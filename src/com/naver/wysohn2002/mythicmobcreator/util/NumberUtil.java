/*******************************************************************************
 *     Copyright (C) 2017 wysohn
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.naver.wysohn2002.mythicmobcreator.util;

import java.util.regex.Pattern;

public class NumberUtil {
    public static void main(String[] ar){
        System.out.println(integer.matcher("1").matches());
        System.out.println(rational.matcher("1.2").matches());
        System.out.println(integerRange.matcher("1-3").matches());
        System.out.println(rationalRange.matcher("1.0-1.5").matches());
    }

    /**
     * ... -5,-4,-3,-2,-1,0,1,2,3,4,5 ... ex) 1
     */
    public static final Pattern integer = Pattern.compile("\\d+");
    /**
     * ... -0.05 ... 0 ... 0.05 ... ex) 0.1
     */
    public static final Pattern rational = Pattern.compile("\\d*\\.?\\d+");
    /**
     * ... -5,-4,-3,-2,-1,0,1,2,3,4,5 ... - ... -5,-4,-3,-2,-1,0,1,2,3,4,5 ... ex) 0-3
     */
    public static final Pattern integerRange = Pattern.compile("\\d+-\\d+");
    /**
     * ... -0.05 ... 0 ... 0.05 ... - ... -0.05 ... 0 ... 0.05 ... ex) 0.5-0.8
     */
    public static final Pattern rationalRange = Pattern.compile("\\d*\\.?\\d+-\\d*\\.?\\d+");

	public static Number toNumber(String num){
		if(num == null || num.length() < 1)
			return null;

		if(num.contains(".")){
			return Double.parseDouble(num);
		}else{
			return Integer.parseInt(num);
		}
	}
}
