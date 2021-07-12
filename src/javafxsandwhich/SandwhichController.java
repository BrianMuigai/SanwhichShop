/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsandwhich;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 */
public class SandwhichController implements Initializable {

    @FXML
    private ListView<String> listview;

    @FXML
    ListView<CheckBox> cbMainIngredIent;

    @FXML
    private ComboBox<String> comboBreadType;
    
    @FXML
    private CheckBox comboLetuce, comboTomato,comboCabbage;
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private Text total;

    private ObservableList<String> sandwhiches;
    private Map<String, Double> mainIngredientsPrices;
    private ObservableList<String> breadTypes;
    
    private List<Order> orders;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sandwhiches = FXCollections.observableArrayList();
        sandwhiches.add("Normal Chicken Sandwich");
        sandwhiches.add("Fish Sandwich");
        sandwhiches.add("Fried Egg Sandwich");
        sandwhiches.add("Grilled Cheese Sandwich");
        sandwhiches.add("Grilled Chicken Sandwich");
        sandwhiches.add("Ham Sandwich");
        sandwhiches.add("Ice Cream Sandwich");
        sandwhiches.add("Meat Ball Sandwich");

        //initializing the bread types
        breadTypes = FXCollections.observableArrayList();
        breadTypes.add("white");
        breadTypes.add("whole wheat");
        breadTypes.add("sourdough");

        //initializing the ingredients
        ObservableList<CheckBox> checkBoxes = FXCollections.observableArrayList();
        checkBoxes.add(new CheckBox("Ingredient 1"));
        checkBoxes.add(new CheckBox("Ingredient 2"));
        checkBoxes.add(new CheckBox("Ingredient 3"));
        checkBoxes.add(new CheckBox("Ingredient 4"));

        mainIngredientsPrices = new HashMap<>();
        mainIngredientsPrices.put("Ingredient 1", 5.0);
        mainIngredientsPrices.put("Ingredient 2", 10.5);
        mainIngredientsPrices.put("Ingredient 3", 15.0);
        mainIngredientsPrices.put("Ingredient 4", 15.25);

        //populating the user interface
        listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listview.setItems(sandwhiches);
        comboBreadType.setItems(breadTypes);
        cbMainIngredIent.setItems(checkBoxes);
        
        orders = new ArrayList<>();

    }
    
    /**
     * Handle placing orders
     * @param e 
     */
    @FXML
    private void order(ActionEvent e){
        String selectedSandwhich = listview.getSelectionModel().getSelectedItem();
//        get selected main ingredients
        List<String> selectedMainIngredients = new ArrayList<>();
        List<Double> selectedMainIngredientsPrices = new ArrayList<>();
        for (CheckBox cb : cbMainIngredIent.getItems()){
            if (cb.isSelected()){
                selectedMainIngredients.add(cb.getText());
                selectedMainIngredientsPrices.add(mainIngredientsPrices.get(cb.getText()));
            }
        }
        
        String selectedBreadType = comboBreadType.getSelectionModel().getSelectedItem();
        
        List<String> additionalIngredients = new ArrayList<>();
        if (comboCabbage.isSelected())
            additionalIngredients.add("Cabbage");
        if (comboLetuce.isSelected())
            additionalIngredients.add("Letuce");
        if (comboTomato.isSelected())
            additionalIngredients.add("Tomato");
        
        //validate data
        if (selectedMainIngredients.size() < 3){
            showErrorDialog("Please select atleast three main ingredients");
            return;
        }else if (selectedSandwhich == null){
            showErrorDialog("Please select a sandwhich");
            return;
        }else if (selectedBreadType == null){
            showErrorDialog("Please select a bread type");
            return;
        }
        
        Order order = new Order();
        order.setAdditionalIngredients(additionalIngredients);
        order.setBreadType(selectedBreadType);
        order.setSandwhich(selectedSandwhich);
        order.setMainIngredients(selectedMainIngredients);
        order.setMainIngredientsPrices(selectedMainIngredientsPrices);
        
        orders.add(order);
        populateOrders();//updates the user interface (left panel)
        System.out.println(order);
    }
    
    /**
     * Draws a list of orders on the left panel
     */
    private void populateOrders(){
        VBox vbox = new VBox(5);
        int total = 0;
        for (Order order: orders){
            VBox child = new VBox();
            Text sandwhich = new Text(order.getSandwhich());
            Text labelMainIngredients = new Text("Main Ingredients: ");
            VBox mainIngredients = new VBox();
            mainIngredients.setPadding(new Insets(0, 0, 0, 10));
            for (String ingredient : order.getMainIngredients()){
                mainIngredients.getChildren().add(new Text(" - " +ingredient));
            }
            Text breadType = new Text("Bread Type: "+order.getBreadType());
            Text labelAdditionalIngredient = new Text("Additional Ingredients: ");
            VBox additionalIngredients = new VBox();
            for (String additionalIngredient : order.getAdditionalIngredients()){
                additionalIngredients.getChildren().add(new Text(" - "+additionalIngredient));
            }
            child.getChildren().addAll(sandwhich, labelMainIngredients, mainIngredients, 
                    breadType, labelAdditionalIngredient,  additionalIngredients, 
                    new Separator());
            vbox.getChildren().add(child);
            
            for (Double d : order.getMainIngredientsPrices()){
                total += d;
            }
        }
        scrollPane.setContent(vbox);
        this.total.setText(""+total);// using 'this' calling the main total from class which is a text object
    }
       
    /**
     * show an alert dialog with error message
     * @param message 
     */
    private void showErrorDialog(String message){
       Alert alert = new Alert(Alert.AlertType.ERROR);
       alert.setTitle("Invalid data");
       alert.setHeaderText(null);
       alert.setContentText(message);
       alert.show();
   }

}
