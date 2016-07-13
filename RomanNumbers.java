public class RomanNumbers{

  public static String[] romanArray = new String[] {"C","XC","L","XL","X","IX","V","IV","I"};
  public static int[] romanIntArray = new int[] {100,90,50,40,10,9,5,4,1};

  public static void main(String[] args) {

    try{
      roman(Integer.parseInt(args[0]));}
      catch (Exception e){

      }
  }

 public static String roman(int n){

  String output = "";
  int counter;

  for(int i=0;i<romanArray.length;i++){
    int romanInt = romanIntArray[i];
    counter = n/romanInt;
    n = n%romanInt;

    for (int j=0;j<counter;j++) {
      output += romanArray[i];
    }
  }
  return output;
 }
}
