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
		while(addend1.length()<addend2.length()) addend1="0"+addend1;
		while(addend2.length()<addend1.length()) addend2="0"+addend2;
		if(addend1sign.equals("+")&&addend2sign.equals("+")) {
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
		}else if(addend1sign.equals("-")&&addend2sign.equals("-")) {
			return hugeIntAdd(addend1obj.setSign("+"),addend2obj.setSign("+")).setSign("-");
		}else if((addend1sign.equals("-")&&addend2sign.equals("+"))) {
			if(!addend1.equals(addend2)) {
				Int[] ltg = hugeIntsAbsLTG(new Int[] {addend1obj,addend2obj});
				if(ltg[1].equals(addend1obj)) return hugeIntSubtract(addend1obj.setSign("+"),addend2obj.setSign("+")).setSign("-");
				else return hugeIntSubtract(addend2obj.setSign("+"),addend1obj.setSign("+"));
			}else return Int.createInt("0");
		}else {
			return hugeIntSubtract(addend1obj.setSign("+"),addend2obj.setSign("+"));
		}
	}
	public static Int hugeIntsAdd(Int[] addends) {
		String sum = "0";
		for(int i=0;i<addends.length;i++) {
			sum = JavaMath.hugeIntAdd(Int.createInt(sum), addends[i]).getValue();
		}
		return Int.createInt(sum);
	}
	public static Int hugeIntSubtract(Int minuendobj, Int subtrahendobj) {
		String subtrahend = subtrahendobj.getAbsValue();
		StringBuilder minuend = new StringBuilder(minuendobj.getAbsValue());
		String minuendsign = minuendobj.getSign();
		String subtrahendsign = subtrahendobj.getSign();
		while(minuend.length()<subtrahend.length()) minuend = new StringBuilder("0" + minuend.toString());
		while(subtrahend.length()<minuend.length()) subtrahend="0"+subtrahend;
		if(minuendsign.equals("-") && subtrahendsign.equals("-")) {
			return hugeIntSubtract(subtrahendobj.setSign("+"),minuendobj.setSign("+"));
		}else if(minuendsign.equals("+") && subtrahendsign.equals("-")) {
			return hugeIntAdd(Int.createInt(minuend.toString()),Int.createInt(subtrahend));
		}else if(minuendsign.equals("-") && subtrahendsign.equals("+")) {
			return hugeIntAdd(Int.createInt(minuend.toString()).setSign("+"),Int.createInt(subtrahend).setSign("+")).setSign("-");
		}else {
			if(!minuend.toString().equals(subtrahend)) {
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
			}else return Int.zero();
		}
	}
	public static Int hugeIntsMultiply(Int[] factors) {
		String product="1";
		for(int i=0;i<factors.length;i++) {
			product=hugeIntMultiply(Int.createInt(product),factors[i]).getValue();
		}
		return Int.createInt(product);
	}
	public static Int hugeIntMultiply(Int factor1obj, Int factor2obj) {
		String factor1=factor1obj.getAbsValue();
		String factor2=factor2obj.getAbsValue();
		Int[] addends = new Int[factor2.length()];
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
			addends[addendindex]=Int.createInt(addendbuilder.toString());
			addendindex++;
			zeroes.append("0");
		}
		String sign="";
		if(factor1obj.getSign().equals(factor2obj.getSign())) {
			sign="+";
		}else {
			sign="-";
		}
		return hugeIntsAdd(addends).setSign(sign);
	}
	/*public static Int hugeIntExp(Int base, long exponent) { //Commented out until division is supported for negative exponents, which won't be until decimals are supported.
		String product="1";
		for(int i=0;i<exponent;i++) {
			product = hugeIntMultiply(Int.createInt(product),base).getValue();
		}
		return Int.createInt(product);
	}*/
	public static Int[] hugeIntsAbsLTG(Int[] intsinput) {
		Int[] ints = intsinput.clone();
		Comparator<Int> compareAbsValues = new Comparator<Int>() {
			public int compare(Int int1, Int int2) {
				return int1.getAbsValue().compareTo(int2.getAbsValue());
			}
		};
		List<Int> intslist = new ArrayList<Int>();
		int maxlength=0;
		for(int i=0;i<ints.length;i++){
			if(ints[i].getAbsValue().length()>maxlength) maxlength = ints[i].getAbsValue().length();
		}
		for(int i=0;i<ints.length;i++){
			String cur = ints[i].getAbsValue();
			while(cur.length()<maxlength) cur = "0"+cur;
			intslist.add(ints[i].setAbsValue(cur));
		}
		Collections.sort(intslist, compareAbsValues);
		for(int i=0;i<intslist.size();i++) {
			String cur = intslist.get(i).getAbsValue();
			Int curint = Int.createInt("0");
			for(int j=0;j<intsinput.length;j++) {
				if(intslist.get(i).equals(intsinput[j])) {
					curint=intsinput[j];
					break;
				}
			}
			while(cur.length()>curint.getAbsValue().length()) cur = cur.substring(1,cur.length());
			ints[i] = intslist.get(i).setAbsValue(cur);
		}
		return ints;
	}
	public static Int[] hugeIntsAbsGTL(Int[] intsinput) {
		Int[] ints = intsinput.clone();
		Comparator<Int> compareAbsValues = new Comparator<Int>() {
			public int compare(Int int1, Int int2) {
				return int1.getAbsValue().compareTo(int2.getAbsValue());
			}
		};
		List<Int> intslist = new ArrayList<Int>();
		int maxlength=0;
		for(int i=0;i<ints.length;i++){
			if(ints[i].getAbsValue().length()>maxlength) maxlength = ints[i].getAbsValue().length();
		}
		for(int i=0;i<ints.length;i++){
			String cur = ints[i].getAbsValue();
			while(cur.length()<maxlength) cur = "0"+cur;
			intslist.add(ints[i].setAbsValue(cur));
		}
		Collections.sort(intslist, compareAbsValues);
		Collections.reverse(intslist);
		for(int i=0;i<intslist.size();i++) {
			String cur = intslist.get(i).getAbsValue();
			Int curint = Int.createInt("0");
			for(int j=0;j<intsinput.length;j++) {
				if(intslist.get(i).equals(intsinput[j])) {
					curint=intsinput[j];
					break;
				}
			}
			while(cur.length()>curint.getAbsValue().length()) cur = cur.substring(1,cur.length());
			ints[i] = intslist.get(i).setAbsValue(cur);
		}
		return ints;
	}
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
	public static Int hugeIntFactorial(Int num){
		if(num.getSign().equals("-")) {
			System.out.println("Factorial cannot be calculated for Int "+num.getValue()+". Number is negative.");
			return null;
		}else {
			String index="1";
			String product="1";
			while(!index.equals(hugeIntAdd(num,Int.pos_one()).getValue())){
				product = hugeIntMultiply(Int.createInt(product),Int.createInt(index)).getValue();
				index=hugeIntAdd(Int.createInt(index), Int.pos_one()).getValue();
			}
			return Int.createInt(product);
		}
	}
}
