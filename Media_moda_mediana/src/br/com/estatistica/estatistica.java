package br.com.estatistica;
import java.util.*;
import java.util.stream.IntStream;

public class estatistica {
    public static int average(int[] elements) {
        int soma = IntStream.of(elements).sum();
        System.out.println(soma/ elements.length);
        return soma / elements.length;
    }

    public static int mode(int[] elements) {
        Map<Integer, Integer> map = new HashMap<>();
        //hasmap , lista com cada indice vinculado a um elemento que tambem sera um inteiro
        int max = 0;
        int mode = 0;
        for (int element : elements) {
            map.put(element, map.getOrDefault(element, 0) + 1);
            // https://www.geeksforgeeks.org/hashmap-getordefaultkey-defaultvalue-method-in-java-with-examples/
            // primeiro elemento do put é a chave ,entao vou passar o proprio elemento como chave ,
            // e o valor dele sera o retorno de getOrDefault , que vai me retornar 0 + 1 se nao encontrar o elemento
            // e se encontrar vai retornar o valor atual dele mais um , que na primeira verificaçção começa ja somando 1
            // o getOrDefault vai me retornar somente se encontrar a chave do elemento

            if (map.get(element) >= max) {
                // map.get retorna o valor mapeado por uma chave , no caso a chave do elemento vai me retornar
                // quantas vezes ele apareceu que é o retorno do getOrDefault
                //https://www.geeksforgeeks.org/map-get-method-in-java-with-examples/
                // a cada rodada atualiza a moda
                max = map.get(element);
                mode = element;
            }
        }
        return mode;
    }

    public static int median(int[] elements) {
        Arrays.sort(elements);
        int mediana;
        if (elements.length % 2 == 0) {
            mediana = (elements[elements.length / 2] + elements[elements.length / 2 - 1]) / 2;
            return mediana;
        } else {
            mediana = elements[elements.length / 2];
            return mediana;
        }

    }

}
