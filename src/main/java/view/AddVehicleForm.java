package view;

import javax.swing.*;
import Controller.*;
import models.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.Console;

public class AddVehicleForm extends JFrame{

    //declaring the components
    JLabel vehicleLabel, typeLabel, crossingTimeLabel, directionLabel, 
    		lengthLabel, emissionLabel, statusLabel, segmentLabel, error;
    
    JTextField vehicleTxtField, crossingTimeTxtField, lengthTxtField,
    		emissionTxtField;
    
    JComboBox<Object> type, direction, status, segment;
    
    JButton addBtn, cancelBtn;
    
    String[] vehicleType = { "CAR", "BUS", "TRUCK" };
    String[] directions = { "LEFT", "RIGHT", "STRAIGHT" };
    String[] vehicleStatus = { "WAITING", "CROSSED" };
    String[] segments = { "S1", "S2", "S3", "S4" };
    
    
    
    //constructor
    public AddVehicleForm(JFrame frame){
        //set the title
        setTitle("New Vehicle");
        setVisible(true);
        setLocationRelativeTo(null);
        setLayout(null);
        setSize(350, 350);
  
        //instantiate the components
        
        //---Labels---//
        vehicleLabel = new JLabel("Vehicle:");
        typeLabel = new JLabel("Type:");
        crossingTimeLabel = new JLabel("Crossing Time:");
        directionLabel = new JLabel("Direction:");
        lengthLabel = new JLabel("Length:");
        emissionLabel = new JLabel("Emission:");
        statusLabel = new JLabel("Status:");
        segmentLabel = new JLabel("Segment:");
        error = new JLabel();
        error.setForeground(Color.red);
        
        //---TextFields---//
        vehicleTxtField = new JTextField();
        crossingTimeTxtField = new JTextField();
        lengthTxtField = new JTextField();
        emissionTxtField = new JTextField();
        
        //---ComboBox---//
        type = new JComboBox<Object>(vehicleType);
        direction = new JComboBox<Object>(directions);
        status = new JComboBox<Object>(vehicleStatus);
        segment = new JComboBox<Object>(segments);
        
        
        //---Buttons---//
        addBtn = new JButton("Add");
        cancelBtn = new JButton("Cancel");
        
        //set the bounds
        vehicleLabel.setBounds(10,0,100,30);
        vehicleTxtField.setBounds(120,0,150,30);
        
        typeLabel.setBounds(10,30,100,30);
        type.setBounds(120,30,150,30);
        
        crossingTimeLabel.setBounds(10,60,100,30);
        crossingTimeTxtField.setBounds(120,60,150,30);
        
        directionLabel.setBounds(10, 90, 100, 30);
        direction.setBounds(120, 90, 150, 30);
        
        lengthLabel.setBounds(10, 120, 100, 30);
        lengthTxtField.setBounds(120, 120, 150, 30);
        
        emissionLabel.setBounds(10, 150, 100, 30);
        emissionTxtField.setBounds(120, 150, 150, 30);
        
        statusLabel.setBounds(10, 180, 100, 30);
        status.setBounds(120, 180, 150, 30);
        
        segmentLabel.setBounds(10, 210, 100, 30);
        segment.setBounds(120, 210, 150, 30);
        
        addBtn.setBounds(10, 275, 100, 30);
        cancelBtn.setBounds(120, 275, 100, 30);
        
        error.setBounds(30, 250, 350, 30);
       
        //add the components
        add(vehicleLabel);
        add(vehicleTxtField);
        
        add(typeLabel);
        add(type);
        
        add(crossingTimeLabel);
        add(crossingTimeTxtField);
        
        add(directionLabel);
        add(direction);
        
        add(lengthLabel);
        add(lengthTxtField);
        
        add(emissionLabel);
        add(emissionTxtField);
        
        add(statusLabel);
        add(status);
        
        add(segmentLabel);
        add(segment);
        
        add(addBtn);
        add(cancelBtn);

        add(error);
        
        //---VALIDATIONS---//
        vehicleFieldValidations();
        crossingTimeValidations();
        lengthFieldValidations();
        emissionFieldValidations();
        
        //---ADD BUTTON---//
        addBtn.addActionListener(ae -> {
        	if(validateInput()) {
        		error.setText("");
        		Vehicle vehicle = new Vehicle(vehicleTxtField.toString(),VehicleType.valueOf(type.getSelectedItem().toString()),
        				Integer.parseInt(crossingTimeTxtField.getText()),Direction.valueOf(direction.getSelectedItem().toString()),
        				Integer.parseInt(lengthTxtField.getText()),Integer.parseInt(emissionTxtField.getText()),
        				Status.valueOf(status.getSelectedItem().toString()),Segment.valueOf(segment.getSelectedItem().toString()));
        		//System.out.println(vehicle.toString());
        	}
//        	System.out.println();
        		
        });
        
        
        //---CANCEL BUTTON---//
        cancelBtn.addActionListener(ce -> {
        	setVisible(false);
        	frame.setEnabled(true);
        });
    }

	private boolean validateInput() {
    	
    	if (vehicleTxtField.getText().length() == 0 || vehicleTxtField.getText().length() < 6) {
    		error.setText("* Invalid vehicle number");
		}
    	else if(crossingTimeTxtField.getText().length() == 0 || Integer.parseInt(crossingTimeTxtField.getText()) == 0) {
    		error.setText("* Crossing Time can't be zero");
    	}
    	else if(lengthTxtField.getText().length() == 0 ||Integer.parseInt(lengthTxtField.getText()) == 0) {
    		error.setText("* Length can't be zero");
    	}
    	else if (emissionTxtField.getText().length() == 0 || Integer.parseInt(emissionTxtField.getText()) == 0){
    		error.setText("* Emission can't be zero");
		}
    	else {
			return true;
		}
    	
		return false;
		
	}

	private void emissionFieldValidations() {
    	emissionTxtField.addKeyListener(new KeyAdapter() {
    		public void keyPressed(KeyEvent e) {
    			char c = e.getKeyChar();
    			
    			if(Character.isDigit(c) || Character.isISOControl(c)) {
    				if (emissionTxtField.getText().length() < 2) {
    					emissionTxtField.setEditable(true);
					} else {
        				if(Character.isISOControl(c))
        					emissionTxtField.setEditable(true);
        				else
        					emissionTxtField.setEditable(false);
        			}
  
    			} else {
    				emissionTxtField.setEditable(false);
				}
			}
		});
	}

	private void lengthFieldValidations() {
		lengthTxtField.addKeyListener(new KeyAdapter() {
    		public void keyPressed(KeyEvent e) {
    			char c = e.getKeyChar();
    			
    			if(Character.isDigit(c) || Character.isISOControl(c)) {
    				if (lengthTxtField.getText().length() < 2) {
    					lengthTxtField.setEditable(true);
					} else {
        				if(Character.isISOControl(c))
        					lengthTxtField.setEditable(true);
        				else
        					lengthTxtField.setEditable(false);
        			}
  
    			} else {
    				lengthTxtField.setEditable(false);
				}
			}
		});
	}

	private void crossingTimeValidations() {
    	crossingTimeTxtField.addKeyListener(new KeyAdapter() {
    		public void keyPressed(KeyEvent e) {
    			char c = e.getKeyChar();
    			
    			if(Character.isDigit(c) || Character.isISOControl(c)) {
    				if (crossingTimeTxtField.getText().length() < 2) {
						crossingTimeTxtField.setEditable(true);
					} else {
        				if(Character.isISOControl(c))
        					crossingTimeTxtField.setEditable(true);
        				else
        					crossingTimeTxtField.setEditable(false);
        			}
  
    			} else {
    				crossingTimeTxtField.setEditable(false);
				}
			}
		});
	}

    private void vehicleFieldValidations(){
    	vehicleTxtField.addKeyListener(new KeyAdapter() {
    		public void keyPressed(KeyEvent e) {
    			char c = e.getKeyChar();
    			
    			if(Character.isLetter(c) || Character.isDigit(c) || Character.isWhitespace(c) || Character.isISOControl(c)) {
    				if (vehicleTxtField.getText().length() < 6) {
    					vehicleTxtField.setEditable(true);
    				}
        			else {
        				if(Character.isISOControl(c))
        					vehicleTxtField.setEditable(true);
        				else
        					vehicleTxtField.setEditable(false);
        			}
				} else {
    					vehicleTxtField.setEditable(false);
					//error.setText("* Enter only alphanumeric characters");
				}

			}
		});
    }

   
}