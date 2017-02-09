package br.org.ons.sager.read.domain;

import java.util.Comparator;

public class DispComparator implements Comparator<Disp>{

	@Override
	public int compare(Disp o1, Disp o2) {
		
		 int sComp = o1.getInstalacao().compareTo(o2.getInstalacao());

         if (sComp != 0) {
            return sComp;
         } else {
        	return o1.getData().compareTo(o2.getData());
         }
	}

}
