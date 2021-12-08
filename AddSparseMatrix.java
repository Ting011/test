//package homework.matrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class AddSparseMatrix {

    public static class Node{
        int col;
        int val;
        Node next;
    }

    public  static class Link{

        public Link(){
            start = null;
        }

        Node start;
    }

    public static void collect(Link [] matrix, BufferedReader br){
        String line;
        try{
            while((line = br.readLine())!=null){
                String str1[] = line.split(" ");
                int row = Integer.parseInt(str1[0]);

                Link temp_Link = new Link();
                Node start_node = new Node();

                for (int i=1;i<str1.length;i++){

                    if(!str1[1].equals(":")){
                        String spilt_str1[] = str1[i].split(":");
                        int col = Integer.parseInt(spilt_str1[0]);
                        int val = Integer.parseInt(spilt_str1[1]);
                        Node temp = new Node();
                        temp.col = col;
                        temp.val = val;

                        if (temp_Link.start==null){
                            temp_Link.start = temp;
                            start_node = temp;

                        }else {
                            temp_Link.start.next = temp;
                            temp_Link.start = temp;
                        }
                    }
                }
                temp_Link.start = start_node;
                matrix[row-1] = temp_Link;
            }
        }catch (Exception e){
            System.out.println("collect error");
        }
    }

    public static void add_node(Link row_link, int col, int val){

        if (row_link.start==null){
            Node added = new Node();
            added.col = col;
            added.val = val;
            added.next = null;
            row_link.start = added;
            return;
        }

        Node temp = row_link.start;
        while (true){
            if (col==row_link.start.col){
                row_link.start.val+=val;
                row_link.start = temp;
                return;
            }

            if (row_link.start.next==null){break;}
            if (col<row_link.start.next.col){break;}
            row_link.start = row_link.start.next;
        }

        Node added = new Node();
        added.col = col;
        added.val = val;
        if(col<row_link.start.col){
            added.next = row_link.start;
            row_link.start = added;
            return;
        }else {
            added.next = row_link.start.next;
            row_link.start.next= added;
        }
        row_link.start = temp;
    }

    public static void add(Link [] matrix, BufferedReader br){
        String line;
        try{
            br.readLine();
            while((line = br.readLine())!=null){
                String str1[] = line.split(" ");
                int row = Integer.parseInt(str1[0]);
                for (int i=1;i<str1.length;i++){

                    if(!str1[1].equals(":")){
                        String spilt_str1[] = str1[i].split(":");
                        int col = Integer.parseInt(spilt_str1[0]);
                        int val = Integer.parseInt(spilt_str1[1]);
                        add_node(matrix[row-1], col, val);

                    }
                }
            }
        }catch (Exception e){
            System.out.println("add error:"+e);
        }
    }

    public static void write(Link [] matrix, BufferedWriter bw){
        try {
            for (int i = 0; i < matrix.length; i++) {
                Link temp = matrix[i];
                String row = String.valueOf(i+1);
                bw.write(row + " ");
                while (temp.start != null) {
                    String pre = String.valueOf(temp.start.col);
                    String suf = String.valueOf(temp.start.val);
                    String element = pre+":"+suf+" ";

                    if(temp.start.val!=0){
                        bw.write(element);
                    }

                    temp.start = temp.start.next;
                }
                bw.newLine();
            }
        }catch (Exception e){
            System.out.println("write error");
        }
    }

    public static void main(String[] args) {

        try {
            FileReader fr1 = new FileReader(args[0]);
            BufferedReader br1 = new BufferedReader(fr1);
            FileReader fr2 = new FileReader(args[1]);
            BufferedReader br2 = new BufferedReader(fr2);
            FileWriter fw = new FileWriter(args[2]);
            BufferedWriter bw = new BufferedWriter(fw);

//            Map<Pair<Integer,Integer>,Integer> matrix = new HashMap<>();
            //basic info
            String basic_info = br1.readLine();
            String []basic = basic_info.split(",");
            int rows = Integer.parseInt(basic[0]);
            Link [] matrix = new Link[rows];

            //put matrix1 into array
            collect(matrix,br1);

            //add together
            add(matrix,br2);

            //output
            bw.write(basic_info);
            bw.newLine();
            write(matrix,bw);

            //save
            bw.flush();
            bw.close();

        }catch (Exception e){
            System.out.println("main error:"+e);
        }
    }
}
