/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsandwhich;

import java.util.List;

/**
 *
 * @author Bri
 */
public class Order {
    
    private String sandwhich;
    private List<String> mainIngredients;
    private List<Double> mainIngredientsPrices;
    private String breadType;
    private List<String> additionalIngredients;

    public String getSandwhich() {
        return sandwhich;
    }

    public void setSandwhich(String sandwhich) {
        this.sandwhich = sandwhich;
    }

    public List<String> getMainIngredients() {
        return mainIngredients;
    }

    public void setMainIngredients(List<String> mainIngredients) {
        this.mainIngredients = mainIngredients;
    }

    public List<Double> getMainIngredientsPrices() {
        return mainIngredientsPrices;
    }

    public void setMainIngredientsPrices(List<Double> mainIngredientsPrices) {
        this.mainIngredientsPrices = mainIngredientsPrices;
    }

    public String getBreadType() {
        return breadType;
    }

    public void setBreadType(String breadType) {
        this.breadType = breadType;
    }

    public List<String> getAdditionalIngredients() {
        return additionalIngredients;
    }

    public void setAdditionalIngredients(List<String> additionalIngredients) {
        this.additionalIngredients = additionalIngredients;
    }

    @Override
    public String toString() {
        return "Order{" + "sandwhich=" + sandwhich + ", mainIngredients=" + mainIngredients + ", mainIngredientsPrices=" + mainIngredientsPrices + ", breadType=" + breadType + ", additionalIngredients=" + additionalIngredients + '}';
    }
       
    
}
