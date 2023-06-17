package pt.isec.pa.tinypac.model.data;

import java.io.*;
import java.util.*;

public class Top5 implements Serializable{

    private final static String folderName = "top5/";
    private final static String fileName = "top5.json";
    private List<Top5Data> top5;

    public Top5(){
        top5 = new ArrayList<>();
    }

    public Boolean readTop5FromFile(){

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(new File(folderName + fileName))
        )){
            top5 = (List<Top5Data>) ois.readObject();
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    private Boolean writeTop5IntoFile(){

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(new File(folderName + fileName))
        )){
            oos.writeObject(top5);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Boolean verifyIfIsInTop5(Integer value){

        readTop5FromFile();

        if(top5.isEmpty() || top5.size() < 5)
            return true;

        for(int i = 0; i < top5.size(); i++){
            if(top5.get(i).getScore() < value)
                return true;
        }
        return false;
    }


    public void addIntoTop5(String userName, Integer score){

        readTop5FromFile();

        top5.add(new Top5Data(userName, score));
        orderTop5();
        if(top5.size() > 5)
            top5.remove(top5.size() - 1);

        writeTop5IntoFile();
    }

    public void orderTop5(){
        top5.sort(Comparator.comparing(Top5Data::getScore).reversed());
        if (top5.size() > 5) {
            top5 = top5.subList(0, 5);
        }

    }

    public List<Top5Data> getTop5() {
        return new ArrayList<>(top5);
    }

    public void printTop5(){
        for (Top5Data aux : top5){
            System.out.println(aux.getUserName() + " " +aux.getScore());
        }
    }
}

class Top5Data implements Serializable{

    private String userName;
    private Integer score;

    Top5Data(String userName, Integer score){
        this.userName = userName;
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public String getUserName() {
        return userName;
    }
}

