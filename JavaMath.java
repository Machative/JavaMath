package aidan.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaMath {
	public static String hugeIntAdd(String addend1, String addend2) {
		StringBuilder sumbuilder = new StringBuilder("");
		if(addend1.length()!=addend2.length()) {
			if(addend1.length()<addend2.length()) {
				while(addend1.length()<addend2.length()) addend1="0"+addend1;
			}else {
				while(addend2.length()<addend1.length()) addend2="0"+addend2;
			}
		}
		int curcarry=0;
		for(int i=addend1.length()-1;i>=0;i--) {
			int presum = Integer.valueOf(String.valueOf(addend1.charAt(i)))+Integer.valueOf(String.valueOf(addend2.charAt(i)))+curcarry;
			if(i==0) sumbuilder.append(new StringBuilder(String.valueOf(presum)).reverse());
			else {
				int sum = presum%10;
				sumbuilder.append(sum);
				curcarry = (presum-sum)/10;
			}
		}
		return sumbuilder.reverse().toString();
	}
	public static String hugeIntsAdd(String[] addends) {
		StringBuilder sumbuilder = new StringBuilder("0");
		for(int i=0;i<addends.length;i++) {
			sumbuilder = new StringBuilder(hugeIntAdd(sumbuilder.toString(),addends[i]));
		}
		
		return sumbuilder.toString();
	}
	public static String hugeIntsMultiply(String[] factors) {
		StringBuilder productbuilder = new StringBuilder("1");
		
		for(int i=0;i<factors.length;i++) {
			productbuilder = new StringBuilder(hugeIntMultiply(productbuilder.toString(),factors[i]));
		}
		
		return productbuilder.toString();
	}
	public static String hugeIntMultiply(String factor1, String factor2) {
		String[] addends = new String[factor2.length()];
		StringBuilder zeroes = new StringBuilder("");
		StringBuilder addendbuilder = new StringBuilder("");
		int addendindex=0;
		for(int i=factor2.length()-1;i>=0;i--) {
			int curcarry=0;
			addendbuilder = new StringBuilder("");
			for(int j=factor1.length()-1;j>=0;j--) {
				int curfac1 = Integer.valueOf(String.valueOf(factor1.charAt(j)));
				int curfac2 = Integer.valueOf(String.valueOf(factor2.charAt(i)));
				int curprod = (curfac1*curfac2)+curcarry;
				if(j==0) {
					addendbuilder.append(new StringBuilder(String.valueOf(curprod)).reverse());
				}else {
					addendbuilder.append(String.valueOf(curprod%10));
					curcarry=(curprod-(curprod%10))/10;
				}
			}
			addendbuilder.reverse();
			addendbuilder = new StringBuilder(addendbuilder.toString()+zeroes.toString());
			addends[addendindex]=addendbuilder.toString();
			addendindex++;
			zeroes.append("0");
		}
		
		return hugeIntsAdd(addends);
	}
	public static String hugeIntExp(String base, long exponent) {
		StringBuilder productbuilder = new StringBuilder("1");
		
		for(int i=0;i<exponent;i++) {
			productbuilder = new StringBuilder(hugeIntMultiply(productbuilder.toString(),base));
		}
		
		return productbuilder.toString();
	}
	public static String[] hugeIntsLTG(String[] inpints){
		List<String> intslist = new ArrayList<String>();
		String[] ints = inpints.clone();
		int maxlength=0;
		for(int i=0;i<ints.length;i++){
			if(ints[i].length()>maxlength) maxlength = ints[i].length();
		}
		for(int i=0;i<ints.length;i++){
			String cur = ints[i];
			while(cur.length()<maxlength) cur = "0"+cur;
			intslist.add(cur);
		}
		Collections.sort(intslist);
		for(int i=0;i<ints.length;i++){
			String cur = intslist.get(i);
			while(cur.charAt(0)=='0') cur = cur.substring(1,cur.length());
			ints[i]=cur;
		}
		return ints;
	}
	public static String[] hugeIntsGTL(String[] inpints){
		List<String> intslist = new ArrayList<String>();
		String[] ints = inpints.clone();
		int maxlength=0;
		for(int i=0;i<ints.length;i++){
			if(ints[i].length()>maxlength) maxlength = ints[i].length();
		}
		for(int i=0;i<ints.length;i++){
			String cur = ints[i];
			while(cur.length()<maxlength) cur = "0"+cur;
			intslist.add(cur);
		}
		Collections.sort(intslist);
		Collections.reverse(intslist);
		for(int i=0;i<ints.length;i++){
			String cur = intslist.get(i);
			while(cur.charAt(0)=='0') cur = cur.substring(1,cur.length());
			ints[i]=cur;
		}
		return ints;
	}
	public static String hugeIntFactorial(String num){
		String index="1";
		String product="1";
		while(!index.equals(hugeIntAdd(num,"1"))){
			product = hugeIntMultiply(product,index);
			index=hugeIntAdd(index, "1");
		}
		return product;
	}
}
