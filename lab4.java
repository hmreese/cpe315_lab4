import java.io.*;
import java.util.*;

class lab4{
    public static void main(String[] args) throws Exception{
        
        object temp = filecall(args[0]);
        command C = new command(args, temp);
        C.runCommands();
    }

    public static object filecall(String arg) throws Exception{
        String string;
        object temp = new object();
        File file = new File(arg);
        BufferedReader buffer = new BufferedReader(new FileReader(file));

        temp.size = 0;
        temp.twoD = new ArrayList<String []>();
        temp.hash = new Hashtable<String, Integer>();

        while ((string = buffer.readLine()) != null){
            filter(string, temp);
        }

        buffer.close();

        return temp;
    }

    public static void filter(String arg, object temp){
        int comment = 0;
        arg = arg.replaceAll("\\$", " \\$");
        arg = arg.replaceAll("\\(", " ");
        arg = arg.replaceAll("\\)", " ");
        arg = arg.replaceAll("#", " # ");
        arg = arg.replaceAll(",", " ");
        arg = arg.replaceAll("\t", " ");
        arg = arg.replaceAll(":", ": ");

        StringBuilder var = new StringBuilder(arg);

        for(int i = 0; i < arg.length(); i++){
            if(arg.charAt(i) == '#' || comment == 1){
                comment = 1;
                var.setCharAt(i, ' ');
            }
        }
        arg = var.toString();
        arg = arg.trim().replaceAll(" +", " ");

        if(arg.isEmpty()){
            return;
        }
        temp.twoD.add(arg.split(" "));

        if((temp.twoD.get(temp.size))[0].contains(":")){
            grabLableAtSize(temp);
        }
        temp.size++;
    }

    public static void grabLableAtSize(object temp){
        String copy[] = new String[(temp.twoD.get(temp.size)).length-1];

        (temp.twoD.get(temp.size))[0].replace(":", "");

        temp.hash.put((temp.twoD.get(temp.size))[0], temp.size);
        for(int j = 1; j < (temp.twoD.get(temp.size)).length; j++){
            copy[j-1] = (temp.twoD.get(temp.size))[j];
        }
    
        if(copy.length == 0){
            temp.twoD.remove(temp.size);
            temp.size--;
        }else{
            temp.twoD.set(temp.size,copy);
        }
    }
}
