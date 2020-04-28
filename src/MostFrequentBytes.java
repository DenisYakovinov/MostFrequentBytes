/* 
Самые частые байты
Ввести с консоли имя файла.
Найти байт или байты с максимальным количеством повторов.
Вывести их на экран через пробел.
Закрыть поток ввода-вывода.


Требования:
1. Программа должна считывать имя файла с консоли.
2. Для чтения из файла используй поток FileInputStream.
3. В консоль через пробел должны выводиться все байты из файла с максимальным количеством повторов.
4. Данные в консоль должны выводится в одну строку.
5. Поток чтения из файла должен быть закрыт.

Most frequent bytes
Enter the file name in the console.
Find the maximum possible number of repetitions.
Display them on the screen with a space.
Close the I / O stream.


Requirements:
1. The program should read the file name from the console.
2. To read from a file, use the FileInputStream stream.
3. The console should include all the files from the file with the maximum number of repetitions with a space.
4. Data in the console should be output in one version.
5. The stream of reading from the file should be closed.

*/

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MostFrequentBytes {

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = null;
        FileInputStream fileForRead = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            fileName = reader.readLine();
        } catch (IOException e) {
        }

        try {
            fileForRead = new FileInputStream(fileName);
            List<Integer> listOfFilesBytes = new ArrayList<>();
            Map<Integer, Integer> mapBytesAndCounts = new HashMap<>();

            while (fileForRead.available() > 0) {
                listOfFilesBytes.add(fileForRead.read());
            }

            for (int i = 0; i < listOfFilesBytes.size(); ) {
                int count = 0;
                for (int x = 1; x < listOfFilesBytes.size(); x++) {
                    if (listOfFilesBytes.get(i).equals(listOfFilesBytes.get(x))) {
                        count++;
                        listOfFilesBytes.remove(x);
                        x--;
                    }
                }
                mapBytesAndCounts.put(listOfFilesBytes.get(i), count);
                listOfFilesBytes.remove(i);
            }

            List<Map.Entry<Integer, Integer>> list = new ArrayList<>(mapBytesAndCounts.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
                @Override
                public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
                    return b.getValue() - a.getValue();
                }
            });
            /*
            list.forEach(new Consumer() {
                @Override
                public void accept(Object o) {
                    Map.Entry<Integer, Integer> o1= (Map.Entry<Integer, Integer>) o;
                    System.out.println(o1.getKey() + "  " + o1.getValue());
                }
            }); */
            final int maxKey = list.get(0).getValue(); //  take the maximum value of the repetition counter
            list.stream().filter(x -> x.getValue() == maxKey).forEach(x -> System.out.print(x.getKey() + " "));

        } catch (IOException e1) {
        } finally {
            try {
                if (fileForRead != null) {
                    fileForRead.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}