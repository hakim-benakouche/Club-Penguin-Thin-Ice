package controllers.impl;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.Controller;
import models.Item;
import models.Model;
import models.impl.Map.DIRECTION;
import models.impl.items.PathMapEnd;

public class ControllerSwing extends JFrame implements Controller {
	
	private Model model;
	
	private JButton up = new JButton("up");
	private JButton left = new JButton("Left");
	private JButton bottom = new JButton("Bottom");
	private JButton right = new JButton("Right");
	
	public ControllerSwing(Model model) {
		super("Controls");
		super.setSize(450,  130);
		this.model = model;
		
		this.initButtons();
		this.initListeners();
		this.disableAllButtons();
	}
	
	private void initButtons() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 1;
		this.left.setSize(150, 30);
		this.left.setPreferredSize(new Dimension(150, 30));
		panel.add(this.left, c);
		
		c.gridx = 1;
		c.gridy = 0;
		this.up.setSize(150, 30);
		this.up.setPreferredSize(new Dimension(150, 30));
		panel.add(this.up, c);
		
		c.gridx = 1;
		c.gridy = 2;
		this.bottom.setSize(150, 30);
		this.bottom.setPreferredSize(new Dimension(150, 30));
		panel.add(this.bottom, c);
		
		c.gridx = 2;
		c.gridy = 1;
		this.right.setSize(150, 30);
		this.right.setPreferredSize(new Dimension(150, 30));
		panel.add(this.right, c);
		
		super.add(panel);
		super.pack();
		super.setVisible(true);
	}
	
	private void initListeners() {
		this.up.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControllerSwing.this.setPlayerMove(DIRECTION.UP);
			}
		});
		
		this.left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControllerSwing.this.setPlayerMove(DIRECTION.LEFT);
			}
		});
		
		this.bottom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControllerSwing.this.setPlayerMove(DIRECTION.DOWN);
			}
		});
		
		this.right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControllerSwing.this.setPlayerMove(DIRECTION.RIGHT);
			}
		});
	}
	
	private void disableAllButtons() {
		this.up.setEnabled(false);
		this.left.setEnabled(false);
		this.bottom.setEnabled(false);
		this.right.setEnabled(false);
	}

	private void enableAllButtons() {
		this.up.setEnabled(true);
		this.left.setEnabled(true);
		this.bottom.setEnabled(true);
		this.right.setEnabled(true);
	}
	
	private DIRECTION lastDirection;
	private void setPlayerMove(DIRECTION direction) {
		this.lastDirection = direction;
		this.disableAllButtons();
	}

	@Override
	public DIRECTION waitForUserInput() {
		while (lastDirection == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this.lastDirection;
	}

	@Override
	public boolean playUserMove() {
		this.enableAllButtons();
		this.waitForUserInput();
		Item item = this.model.getItem(this.lastDirection);
		if (item.isReacheable()) {
			this.model.movePlayer(this.lastDirection);
		} else {
			item.onPush(this.lastDirection);
		}
		this.lastDirection = null;
		return item instanceof PathMapEnd;
	}

	@Override
	public void destroy() {
		super.setVisible(false); //you can't see me!
		super.dispose(); //Destroy the JFrame object
	}
}
