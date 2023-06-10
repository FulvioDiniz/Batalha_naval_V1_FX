package batalha_naval.model;

public class EnumBarco {
    public enum Barco {
        PORTA_AVIAO(5, 100),
        COURACADO(4, 50),        
        SUBMARINO(2, 20);
        
        private int tamanho;
        private int ponto;

        Barco(int tamanho, int ponto) {
            this.ponto = ponto;
            this.tamanho = tamanho;
        }

        public int getTamanho() {
            return tamanho;
        }

        public int getPonto() {
            return ponto;
        }
    }
    
}
