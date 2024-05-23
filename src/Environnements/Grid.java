package environnements;

import types.Effect;

public interface Grid {
    public default String getName() { return this.toString().toLowerCase(); }
    public default Effect get() { return null; }

    public void updateStringCode(String textCode);
    public String getStringCode();

    public Grid[] getValues();
}