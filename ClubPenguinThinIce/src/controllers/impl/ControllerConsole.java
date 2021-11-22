package controllers.impl;

import java.util.Scanner;

import controllers.Controller;
import models.Item;
import models.Model;
import models.impl.Map.DIRECTION;
import models.impl.PathMapEnd;

public class ControllerConsole implements Controller {
	
	
	private final static String MESSAGE_BEFORE_USER_INPUT = "Saisir un déplacement : zqsd ou 2468 puis entrer.";
	private final static String ALLOWED_INPUTS = "ZQSD2486";
	
	private Scanner keyboard = new Scanner(System.in);
	
	private Model model;
	
	
	public ControllerConsole(Model model) {
		this.model = model;
	}
	
	@Override
	public DIRECTION waitForUserInput() {
		System.out.println(ControllerConsole.MESSAGE_BEFORE_USER_INPUT);
		String input = keyboard.nextLine().toUpperCase();
		
		
        while (input.length() != 1 || ! ALLOWED_INPUTS.contains(input)) {
        	System.out.print("\nSaisie invalide. >> ");
        	input = keyboard.nextLine().toUpperCase();
        }
		
        
        if ("Z8".contains(input)) {
        	return DIRECTION.UP;
        } else if ("Q4".contains(input)) {
        	return DIRECTION.LEFT;
        } else if ("S2".contains(input)) {
        	return DIRECTION.DOWN;
        } else if ("D6".contains(input)) {
        	return DIRECTION.RIGHT;
        }
		return null;
	}
	
	@Override
	public boolean playUserMove() {
		DIRECTION direction = this.waitForUserInput();
		Item item = this.model.getItem(direction);
		if (item.isReacheable()) {
			this.model.movePlayer(direction);
		}
		
		return item instanceof PathMapEnd;
	}
	
}
