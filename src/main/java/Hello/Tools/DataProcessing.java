package Hello.Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Alex on 2017/9/3.
 */
public class DataProcessing {

    private int[] columns;
    private String fileName;

    public DataProcessing(String fileName){
        //this.columns=columns;
        this.fileName=fileName;
    }

    public String[] selectColumns(){

        String[] array=null;
        String splitBy = ",";
        try{
            BufferedReader br = new BufferedReader(new FileReader("newfile.csv"));
            String line = br.readLine();
            array = line.split(splitBy);
            //System.out.println(array[0]);
            br.close();
        }catch(IOException e){

        }
        return array;
    }

    public void spliteData(int[] columns){
        this.columns=columns;


    }
}
