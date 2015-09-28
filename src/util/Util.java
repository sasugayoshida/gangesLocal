package util;




public class Util {
	public static int[][] mapStringToInt2DArray(String array){
		/*
		* 引数: "[[1,2,345,-1],[5,5,-123],[1,7,6]]" こんな感じの文字列
		* [[と]]をtrimして ],を区切り文字として一次配列に分解
		* 一次配列を,で区切ってint変換していき、配列に格納する
		* JavaのMapをJSON型に変換するときに使う
		*/
		array = array.replaceAll("\\s","");
		array = array.replaceAll("(\\[\\[|\\]\\])",""); //　[[と]]と改行コードを取り除く
		String[] arrays = array.split("\\],");
		int[][] result = new int[arrays.length][];
		for(int i=0;i<arrays.length;i++){
			String[] target = arrays[i].replaceAll("\\[","").split(",");
			int[] tmp = new int[target.length];
			for(int j=0;j<target.length;j++){
				tmp[j] = Integer.parseInt(target[j]);
			}
			result[i] = tmp;
		}
		return result;
	}
	//MapはJava DB上では描画情報は文字列でやり取りするのでこのメソッドはたぶん使わない

	public static String mapInt2DArrayToString(int[][] array){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int[] i:array){
			sb.append("[");
			for(int j:i){
				sb.append(new Integer(j).toString()).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]").append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");

		return sb.toString();
	}
	//parameterと判定したい型を指定してエラーチェックする
	public static boolean isValid(String arg,String type){
		if(arg == null || arg.equals("")){
			return false;
		}
		if(type.matches("(String|s|S)")){}
		if(type.matches("(int|Int|i|I|Integer)")){try{	Integer.parseInt(arg);}catch(NumberFormatException e){e.printStackTrace();return false;}}
		if(type.matches("(Double|double|d|D)")){try{	Double.parseDouble(arg);}catch(NumberFormatException e){e.printStackTrace();return false;}}
		/*
		if(type.matches("int\\[\\]\\[\\]")){ //""[[ではじまって　]] でおわってるかどうかを調べる。できればもうしこし細かく確認したい
			if(!(arg.charAt(0)=='[' && arg.charAt(1)=='[' && arg.charAt(arg.length()-1)==']' && arg.charAt(arg.length()-2)==']')){
				return false;
			}
		}*/
		return true;
	}
	//型のメソッド一覧を出力する type = "java.lang.String"
	public static void getMethods(String type){
		try {
			Class<?> clazz = Class.forName(type);

			String className = clazz.getName();
			//Method[] methods = clazz.getMethods();
			java.lang.reflect.Method[] methods = clazz.getDeclaredMethods();

			StringBuilder builder = new StringBuilder();
			for (java.lang.reflect.Method method : methods) {
				String methodName = method.getName();
				builder.append(className).append("#");
				builder.append(methodName).append("(");

				Class<?>[] parameterTypes = method.getParameterTypes();
				for (int i = 0; i < parameterTypes.length; i++) {
					Class<?> parameterType = parameterTypes[i];
					builder.append(parameterType.getName());
					if (i + 1 < parameterTypes.length) {
						builder.append(", ");
					}
				}
				builder.append(")");
				builder.append(System.getProperty("line.separator"));
			}
			System.out.println(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}