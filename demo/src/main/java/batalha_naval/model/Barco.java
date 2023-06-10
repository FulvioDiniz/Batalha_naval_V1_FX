package batalha_naval.model;



public class Barco {
    private EnumBarco.Barco tipo;

    public Barco(EnumBarco.Barco tipo) {
        this.tipo = tipo;
    }

    public EnumBarco.Barco getTipo() {
        return tipo;
    }
    
}
