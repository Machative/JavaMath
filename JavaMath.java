package aidan.math;
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
		int addendfactor=1;
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
			addendbuilder = new StringBuilder(String.valueOf(Integer.valueOf(addendbuilder.toString())*addendfactor));
			addends[addendindex]=addendbuilder.toString();
			addendfactor*=10;
			addendindex++;
		}
		return hugeIntsAdd(addends);
	}
}
