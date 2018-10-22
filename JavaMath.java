package aidan.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JavaMath {
	public static Int hugeIntAdd(Int addend1obj, Int addend2obj) {
		StringBuilder sumbuilder = new StringBuilder("");
		String addend1 = addend1obj.getAbsValue();
		String addend2 = addend2obj.getAbsValue();
		String addend1sign = addend1obj.getSign();
		String addend2sign = addend2obj.getSign();
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
		return Int.createInt(sumbuilder.reverse().toString());
	}
	public static Int hugeIntsAdd(Int[] addends) {
		StringBuilder sumbuilder = new StringBuilder("0");
		for(int i=0;i<addends.length;i++) {
			//hugeintsadd if sign is positive, hugeintssubtract if sign is negative
			//sumbuilder = new StringBuilder(hugeIntAdd(Int.createInt(sumbuilder.toString()),addends[i]).getValue());
		}
		
		return Int.createInt(sumbuilder.toString());
	}
	public static Int hugeIntSubtract(Int minuendobj, Int subtrahendobj) {
		String subtrahend = subtrahendobj.getAbsValue();
		StringBuilder minuend = new StringBuilder(minuendobj.getAbsValue());
		String minuendsign = minuendobj.getSign();
		String subtrahendsign = subtrahendobj.getSign();
		if(minuend.length()<subtrahend.length()) {
			while(minuend.length()<subtrahend.length()) minuend = new StringBuilder("0" + minuend.toString());
		}else {
			while(subtrahend.length()<minuend.length()) subtrahend="0"+subtrahend;
		}
		if(minuendsign.equals("-") && subtrahendsign.equals("-")) {//Subtrahend minus the minuend (same as normal subtraction but switch min and sub)
			String sign;
			Int[] ltg = hugeIntsLTG(new Int[] {minuendobj,subtrahendobj});
			if(ltg[0].equals(minuendobj)) sign="-";
			else sign="+";
			StringBuilder newminuend = new StringBuilder(ltg[0].getAbsValue());
			String newsubtrahend = ltg[1].getAbsValue();
			StringBuilder difbuilder = new StringBuilder("");
			for(int i=newminuend.length()-1;i>=0;i--) {
				int curmindig = Integer.valueOf(String.valueOf(newminuend.charAt(i)));
				int cursubdig = Integer.valueOf(String.valueOf(newsubtrahend.charAt(i)));
				if(curmindig>=cursubdig) {
					difbuilder.append(String.valueOf(curmindig-cursubdig));
				}else {
					difbuilder.append(String.valueOf((curmindig+10)-cursubdig));
					newminuend.setCharAt(i-1, Character.forDigit(Integer.valueOf(newminuend.charAt(i-1))-1,10));
				}
			}
			return Int.createInt(sign,difbuilder.reverse().toString());
		}else if(minuendsign.equals("+") && subtrahendsign.equals("-")) {//Addition with positive outcome (500+300)
			return hugeIntAdd(Int.createInt(minuend.toString()),Int.createInt(subtrahend));
		}else if(minuendsign.equals("-") && subtrahendsign.equals("+")) {//Addition with negative outcome ( -(500+300) )
			return hugeIntAdd(Int.createInt(minuend.toString()).setSign("+"),Int.createInt(subtrahend).setSign("+")).setSign("-");
		}else {//Normal subtraction (500-300)
			String sign;
			Int[] ltg = hugeIntsLTG(new Int[] {minuendobj,subtrahendobj});
			if(ltg[0].equals(minuendobj)) sign="-";
			else sign="+";
			StringBuilder newminuend = new StringBuilder(ltg[1].getAbsValue());
			String newsubtrahend = ltg[0].getAbsValue();
			StringBuilder difbuilder = new StringBuilder("");
			for(int i=newminuend.length()-1;i>=0;i--) {
				int curmindig = Character.getNumericValue(newminuend.charAt(i));
				int cursubdig = Character.getNumericValue(newsubtrahend.charAt(i));
				if(curmindig>=cursubdig) {
					difbuilder.append(String.valueOf(curmindig-cursubdig));
				}else {
					difbuilder.append(String.valueOf((curmindig+10)-cursubdig));
					newminuend.setCharAt(i-1, Character.forDigit(Integer.valueOf(String.valueOf(newminuend.charAt(i-1)))-1,10));
				}
			}
			String finalvalue = difbuilder.reverse().toString();
			while(finalvalue.charAt(0)=='0' && finalvalue.length()!=1) {
				finalvalue = finalvalue.substring(1,finalvalue.length());
			}
			return Int.createInt(sign,finalvalue);
		}
	}
	/*public static String hugeIntsMultiply(String[] factors) {
		StringBuilder productbuilder = new StringBuilder("1");
		
		for(int i=0;i<factors.length;i++) {
			productbuilder = new StringBuilder(hugeIntMultiply(productbuilder.toString(),factors[i]));
		}
		
		return productbuilder.toString();
	}*/
	/*public static String hugeIntMultiply(String factor1, String factor2) {
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
	}*/
	/*public static String hugeIntExp(String base, long exponent) {
		StringBuilder productbuilder = new StringBuilder("1");
		
		for(int i=0;i<exponent;i++) {
			productbuilder = new StringBuilder(hugeIntMultiply(productbuilder.toString(),base));
		}
		
		return productbuilder.toString();
	}*/
	public static Int[] hugeIntsLTG(Int[] intsinput){
		Int[] ints = intsinput.clone();
		List<Int> negatives = new ArrayList<Int>();
		List<Int> positives = new ArrayList<Int>();
		int maxlength=0;
		for(int i=0;i<ints.length;i++){
			if(ints[i].getAbsValue().length()>maxlength) maxlength = ints[i].getAbsValue().length();
		}
		for(int i=0;i<ints.length;i++){
			String cur = ints[i].getAbsValue();
			while(cur.length()<maxlength) cur = "0"+cur;
			if(ints[i].getSign().equals("-")) {
				negatives.add(ints[i].setValue(cur));
			}else {
				positives.add(ints[i].setValue(cur));
			}
		}
		Comparator<Int> compareAbsValues = new Comparator<Int>() {
			public int compare(Int int1, Int int2) {
				return int1.getAbsValue().compareTo(int2.getAbsValue());
			}
		};
		Collections.sort(positives, compareAbsValues);
		Collections.sort(negatives, compareAbsValues);
		Collections.reverse(negatives);
		for(int i=0;i<negatives.size();i++){
			String cur = negatives.get(i).getAbsValue();
			Int curint = Int.createInt("0");
			for(int j=0;j<intsinput.length;j++) {
				if(negatives.get(i).equals(intsinput[j])) {
					curint=intsinput[j];
					break;
				}
			}
			while(cur.length()>curint.getAbsValue().length()) cur = cur.substring(1,cur.length());
			ints[i] = negatives.get(i).setValue("-",cur);
		}
		int offset=negatives.size();
		for(int i=0;i<positives.size();i++){
			String cur = positives.get(i).getAbsValue();
			Int curint = Int.createInt("0");
			for(int j=0;j<intsinput.length;j++) {
				if(positives.get(i).equals(intsinput[j])) {
					curint=intsinput[j];
					break;
				}
			}
			while(cur.length()>curint.getAbsValue().length()) cur = cur.substring(1,cur.length());
			ints[i+offset] = positives.get(i).setValue("+",cur);
		}
		return ints;
	}
	public static Int[] hugeIntsGTL(Int[] intsinput){
		Int[] ints = intsinput.clone();
		List<Int> negatives = new ArrayList<Int>();
		List<Int> positives = new ArrayList<Int>();
		int maxlength=0;
		for(int i=0;i<ints.length;i++){
			if(ints[i].getAbsValue().length()>maxlength) maxlength = ints[i].getAbsValue().length();
		}
		for(int i=0;i<ints.length;i++){
			String cur = ints[i].getAbsValue();
			while(cur.length()<maxlength) cur = "0"+cur;
			if(ints[i].getSign().equals("-")) {
				negatives.add(ints[i].setValue(cur));
			}else {
				positives.add(ints[i].setValue(cur));
			}
		}
		Comparator<Int> compareAbsValues = new Comparator<Int>() {
			public int compare(Int int1, Int int2) {
				return int1.getAbsValue().compareTo(int2.getAbsValue());
			}
		};
		Collections.sort(positives, compareAbsValues);
		Collections.reverse(positives);
		Collections.sort(negatives, compareAbsValues);
		for(int i=0;i<positives.size();i++){
			String cur = positives.get(i).getAbsValue();
			Int curint = Int.createInt("0");
			for(int j=0;j<intsinput.length;j++) {
				if(positives.get(i).equals(intsinput[j])) {
					curint=intsinput[j];
					break;
				}
			}
			while(cur.length()>curint.getAbsValue().length()) cur = cur.substring(1,cur.length());
			ints[i] = positives.get(i).setValue("+",cur);
		}
		int offset=positives.size();
		for(int i=0;i<negatives.size();i++){
			String cur = negatives.get(i).getAbsValue();
			Int curint = Int.createInt("0");
			for(int j=0;j<intsinput.length;j++) {
				if(negatives.get(i).equals(intsinput[j])) {
					curint=intsinput[j];
					break;
				}
			}
			while(cur.length()>curint.getAbsValue().length()) cur = cur.substring(1,cur.length());
			ints[i+offset] = negatives.get(i).setValue("-",cur);
		}
		return ints;
	}
	/*public static String hugeIntFactorial(String num){
		String index="1";
		String product="1";
		while(!index.equals(hugeIntAdd(num,"1"))){
			product = hugeIntMultiply(product,index);
			index=hugeIntAdd(index, "1");
		}
		return product;
	}*/
}
