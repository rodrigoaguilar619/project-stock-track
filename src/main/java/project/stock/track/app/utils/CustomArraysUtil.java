package project.stock.track.app.utils;

import java.util.List;

public class CustomArraysUtil {
	
	public boolean compareList(List<String> listOne, List<String> listTwo) {
		
		if (listOne.size() != listTwo.size())
			return false;
					
		for(int i = 0; i < listOne.size(); i++) {
			if (!listOne.get(i).equals(listTwo.get(i)))
				return false;
		}
		
		return true;
		
	}

}
