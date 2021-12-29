package phazerous.flyplugin;

public class Operations {

    public static int IntTryParse(String string){
        try {
            int num = Integer.parseInt(string);

            return num;
        } catch (Exception e) {

            return -1;
        }
    }
}
