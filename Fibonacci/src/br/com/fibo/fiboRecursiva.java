package br.com.fibo;

import java.util.List;
import java.util.ArrayList;

public class fiboRecursiva {
    public static List<Integer> fibonacci() {
        int limite = 14;
        List<Integer> sequencia = new ArrayList<>();
        for (int i = 0;  i<= limite; i++) {
            sequencia.add(fibonacci(i, sequencia));
        }
        System.out.println(sequencia);
        return sequencia;

    }
    private static int fibonacci(int n, List<Integer> sequencia) {
        if (n == 0 || n == 1) {
            return n;
        }
        else {
            return n < sequencia.size()
                    ? sequencia.get(n)
                    : fibonacci(n - 1, sequencia) + fibonacci(n - 2, sequencia);

        }
    }
    public static Boolean isFibonacci(Integer a) {
        return fibonacci().contains(a);
    }
    public static void MOSTRA() {

        System.out.println(isFibonacci(54));
    }
}



