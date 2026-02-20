import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

class Key_actions implements KeyListener {
	public void keyTyped(KeyEvent k) {
		BouncingBall.c_code += k.getKeyChar();
		if (BouncingBall.c_code.length() == 10) {
			if (BouncingBall.c_code.toUpperCase().equals("PAINKILLER")) {
				BouncingBall.c_code = "";
				BouncingBall.level = 6;
			}
		} else if (BouncingBall.c_code.length() == 8) {
			if (BouncingBall.c_code.toUpperCase().equals("INFINITY")) {
				BouncingBall.Bullet_count = Integer.MAX_VALUE;
			}
		} else if (BouncingBall.c_code.length() > 10) {
			BouncingBall.c_code = "";
		}
	}

	public void keyPressed(KeyEvent kx) {
		if (kx.getKeyCode() == KeyEvent.VK_SPACE) {
			if (BouncingBall.bullet_fire == false && !BouncingBall.won) {
				if (BouncingBall.startup_screen == true) {
					BouncingBall.startup_screen = false;
				}
				BouncingBall.Bullet_count--;
				if (BouncingBall.Bullet_count < 0) {
					BouncingBall.Bullet_count = 0;
					BouncingBall.game_over.setVisible(true);
				} else {
					BouncingBall.bullet_fire = true;
					BouncingBall.c_code = "";
					BouncingBall.Bullet_remaining.setText("Bales: " + BouncingBall.Bullet_count);
					BouncingBall.show_popup = false;
					if (BouncingBall.gamemusic && BouncingBall.Bullet_count >= 0) {
						try {
							BufferedInputStream audioFile = new BufferedInputStream(
									BouncingBall.bBall.getResourceAsStream("/res/gunshot.wav"));
							AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
							Clip clip = AudioSystem.getClip();
							clip.open(audioInputStream);
							clip.start();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			} else if (BouncingBall.won) {
				BouncingBall.winner.setVisible(true);
			}
		}
	}

	public void keyReleased(KeyEvent key) {
	}
}

class Listener extends MouseAdapter {
	public void mouseClicked(MouseEvent e) {
		if (BouncingBall.bullet_fire == false && !BouncingBall.won) {
			if (BouncingBall.startup_screen == true) {
				BouncingBall.startup_screen = false;
			}
			BouncingBall.Bullet_count--;
			if (BouncingBall.Bullet_count < 0) {
				BouncingBall.Bullet_count = 0;
				BouncingBall.game_over.setVisible(true);
			} else {
				BouncingBall.bullet_fire = true;
				BouncingBall.c_code = "";
				BouncingBall.Bullet_remaining.setText("Bales: " + BouncingBall.Bullet_count);
				BouncingBall.show_popup = false;
				if (BouncingBall.gamemusic && BouncingBall.Bullet_count >= 0) {
					try {
						BufferedInputStream audioFile = new BufferedInputStream(
								BouncingBall.bBall.getResourceAsStream("/res/gunshot.wav"));
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
						Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} else if (BouncingBall.won) {
			BouncingBall.winner.setVisible(true);
		}
	}
}

class Button_Handler implements ActionListener {
	public static JCheckBox c1, c2;
	public static Boolean selected_BG = true;
	public static Boolean selected_GM = true;
	public static JFrame jf2;

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Guardar")) {
			if (c1.isSelected()) {
				BouncingBall.clip.stop();
				BouncingBall.clip.start();
				BouncingBall.bgmusic = true;
				selected_BG = true;
			} else {
				BouncingBall.clip.stop();
				BouncingBall.bgmusic = false;
				selected_BG = false;
			}
			if (c2.isSelected()) {
				BouncingBall.gamemusic = true;
				selected_GM = true;
			} else {
				BouncingBall.gamemusic = false;
				selected_GM = false;
			}
			jf2.dispose();
		}
		if (e.getActionCommand().equals("Configuració")) {
			jf2 = new JFrame("Configuració");
			jf2.setLayout(null);
			JLabel l1 = new JLabel("Configuració de so");
			JLabel l2 = new JLabel("Fet  per: CIDE");
			jf2.add(l1);
			jf2.add(l2);
			c1 = new JCheckBox("Música de fons");
			c1.setSelected(selected_BG);
			jf2.add(c1);
			c1.setBounds(80, 60, 150, 20);
			c1.setBackground(Color.WHITE);
			c1.setForeground(Color.BLACK);

			c2 = new JCheckBox("Música del joc");
			c2.setSelected(selected_GM);
			jf2.add(c2);
			c2.setBounds(80, 90, 150, 20);
			c2.setBackground(Color.WHITE);
			c2.setForeground(Color.BLACK);

			JButton save = new JButton("Guardar");
			Button_Handler button_Handler = new Button_Handler();
			save.addActionListener(button_Handler);
			jf2.add(save);
			save.setBackground(Color.WHITE);
			save.setBounds(100, 125, 100, 30);

			l1.setBounds(110, 30, 100, 20);
			l2.setBounds(160, 165, 130, 20);
			jf2.getContentPane().setBackground(Color.WHITE);
			jf2.setSize(300, 230);
			jf2.setResizable(false);
			jf2.setLocationRelativeTo(BouncingBall.f);
			jf2.setVisible(true);
		}
		if (e.getActionCommand().equals("Començar de nou")) {
			BouncingBall.Bullet_count = 6;
			BouncingBall.point = 0;
			BouncingBall.ball_speed = 8;
			BouncingBall.target = 20;
			BouncingBall.level = 1;
			BouncingBall.score.setText("Punts: " + BouncingBall.point);
			BouncingBall.Bullet_remaining.setText("Bales: " + BouncingBall.Bullet_count);
			BouncingBall.Next_target.setText("Objectiu: " + BouncingBall.target);
			BouncingBall.Level.setText("Nivell: " + BouncingBall.level);
			BouncingBall.game_over.setVisible(false);
			BouncingBall.winner.setVisible(false);
			BouncingBall.move_ball = true;
			BouncingBall.bullet_fire = false;
			BouncingBall.ballx = 450;
			BouncingBall.bally = 400;
			BouncingBall.won = false;
			if (BouncingBall.bgmusic) {
				try {
					BufferedInputStream audioFile = new BufferedInputStream(
							BouncingBall.bBall.getResourceAsStream("/res/theme.wav"));
					AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(audioFile);
					BouncingBall.clip = AudioSystem.getClip();
					BouncingBall.clip.open(audioInputStream1);
					BouncingBall.clip.start();
					BouncingBall.clip.loop(Clip.LOOP_CONTINUOUSLY);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		if (e.getActionCommand().equals("Sortir")) {
			System.exit(0);
		}
	}
}

public class BouncingBall extends Canvas {
	private static int x = 0, a = 0;
	public static boolean up = true;
	public static boolean move_ball = true;
	public static int ballx = 450;
	public static int bally = 400;
	public static int bulletx = 20;
	public static int bullety = 320;
	public static boolean bullet_fire = false;
	public static boolean bulletf = true;
	public static int point = 0;
	public static int init_x = 450;
	public static boolean score_update = true;
	public static int target = 20;
	public static int level = 1;
	public static int ball_speed = 8;
	public static int area_x, area_y;
	public static JLabel score;
	public static JLabel gun;
	public static Image img, gun_img, bullet_img, ball_img;
	public static BufferedImage bf;
	public static JPanel detail_panel, bottom_buttons1, bottom_buttons2;
	public static JLabel Bullet_remaining;
	public static JLabel Next_target;
	public static JLabel Level;
	public static Integer Bullet_count = 6;
	public static JDialog game_over, winner;
	public static JLabel game_over_msg, winning_msg;
	public static JButton restart, exit, restart2, exit2;
	public static boolean level_changed = false;
	public static int consecutive_hit = 0;
	public static Font pop_font = new Font("Fugaz one", Font.PLAIN, 22);
	public static String popup_msg = "default";
	public static Boolean show_popup = false;
	public static Boolean hit = false;
	public static Clip clip;
	public static AudioInputStream audioInputStream1;
	public static Boolean startup_screen = true;
	public static Class bBall;
	public static Boolean bgmusic = true;
	public static Boolean gamemusic = true;
	public static Boolean won = false;
	public static JFrame f;
	public static String c_code = "";

	public static void main(String[] args) {

		f = new JFrame("El mico disparador");
		f.setVisible(false);

		// crea la ventana del juego
		JFrame frame = new JFrame("Pantalla d'inici");
		frame.setSize(600, 450); // tamano personalizado para la pantalla de inicio
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // cierra programa al cerrar ventana
		frame.setResizable(false); // no permite que el usuario modifique la ventana
		frame.setLocationRelativeTo(null); // centra la ventana en la pantalla

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(new Color(29, 144, 49));

		// gridbagconstraints para estructurar las cosas
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // espaciado entre componentes
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// titulo del juego
		JLabel titulo = new JLabel("El mico disparador", JLabel.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 40));
		titulo.setForeground(Color.WHITE);

		JButton reglas = new JButton("Com jugar");
		reglas.setFont(new Font("Arial", Font.BOLD, 20));
		reglas.setBackground(Color.WHITE);
		reglas.addActionListener(l -> {
			JOptionPane.showMessageDialog(frame, "Fes clic per disparar una bala.\r\n" + //
					"Cada cop dóna 10 punts i colpejar la pilota al centre suma 20 punts.\r\n" + //
					"Recompensa de Bala extra per 2 cops consecutius.\r\n" + //
					"En arribar a la puntuació objectiu, el nivell canvia.");
		});

		// boton de comenzar juego
		JButton begin = new JButton("Començar");
		begin.setFont(new Font("Arial", Font.BOLD, 20));
		begin.setBackground(Color.WHITE);

		// al presionar el boton de comenzar
		begin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// cierra la ventana de inicio
				frame.dispose();

				// abre la ventana del juego
				f.setVisible(true);
				GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
				gd.setFullScreenWindow(f);
			}
		});

		// titulo del juego
		gbc.gridx = 0; // columna 0
		gbc.gridy = 0; // fila 0
		gbc.gridwidth = 2; // ocupa 2 columnas
		panel.add(titulo, gbc);

		// boton de comenzar
		gbc.gridx = 0; // columna 0
		gbc.gridy = 1; // fila 5
		gbc.gridwidth = 2; // ocupa 2 columnas
		panel.add(begin, gbc);

		// boton de instrucciones
		gbc.gridx = 0; // columna 0
		gbc.gridy = 2; // fila 6
		gbc.gridwidth = 2; // ocupa 2 columnas
		panel.add(reglas, gbc);

		frame.add(panel);
		frame.setVisible(true);

		BouncingBall bouncingBall = new BouncingBall();
		bBall = bouncingBall.getClass();
		if (bgmusic) {
			try {
				BufferedInputStream audioFile = new BufferedInputStream(bBall.getResourceAsStream("/res/theme.wav"));
				AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(audioFile);
				clip = AudioSystem.getClip();
				clip.open(audioInputStream1);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// menu bar
		JMenuBar jmb = new JMenuBar();
		jmb.setBackground(Color.WHITE);
		JMenuItem settings = new JMenuItem("Configuració");
		jmb.add(settings);
		jmb.setBackground(Color.WHITE);
		settings.setBackground(Color.WHITE);
		f.setJMenuBar(jmb);

		area_x = 800;
		area_y = 600;
		Button_Handler button_Handler = new Button_Handler();

		detail_panel = new JPanel(null);
		score = new JLabel("Punts: " + point);
		Bullet_remaining = new JLabel("Bales: " + Bullet_count);
		Next_target = new JLabel("Objectiu: " + target);
		Level = new JLabel("Nivell: " + level);
		game_over = new JDialog(f, "Has perdut!");
		winner = new JDialog(f, "Has guanyat!");

		Image g_over = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/gameover_img.png"));
		JLabel g_over_img = new JLabel("", SwingConstants.CENTER);
		g_over_img.setIcon(new ImageIcon(g_over.getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
		game_over.getContentPane().setBackground(Color.WHITE);

		Image w_over = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/winner_img.jpg"));
		JLabel w_over_img = new JLabel("", SwingConstants.CENTER);
		w_over_img.setIcon(new ImageIcon(w_over.getScaledInstance(300, 350, Image.SCALE_DEFAULT)));
		winner.getContentPane().setBackground(Color.WHITE);

		restart = new JButton("Començar de nou");
		restart.setBackground(Color.WHITE);
		restart.setForeground(Color.BLACK);
		exit = new JButton("Sortir");
		exit.setBackground(Color.WHITE);
		exit.setForeground(Color.BLACK);

		restart2 = new JButton("Començar de nou");
		restart2.setBackground(Color.WHITE);
		restart2.setForeground(Color.BLACK);
		exit2 = new JButton("Sortir");
		exit2.setBackground(Color.WHITE);
		exit2.setForeground(Color.BLACK);

		restart.addActionListener(button_Handler);
		exit.addActionListener(button_Handler);
		restart2.addActionListener(button_Handler);
		exit2.addActionListener(button_Handler);
		settings.addActionListener(button_Handler);

		game_over.add(g_over_img);
		winner.add(w_over_img);

		bottom_buttons1 = new JPanel();
		bottom_buttons1.setBackground(Color.WHITE);
		bottom_buttons1.add(restart);
		bottom_buttons1.add(exit);

		bottom_buttons2 = new JPanel();
		bottom_buttons2.add(restart2);
		bottom_buttons2.add(exit2);

		winner.add(bottom_buttons2, BorderLayout.SOUTH);
		game_over.add(bottom_buttons1, BorderLayout.SOUTH);

		game_over.setSize(300, 200);
		winner.setSize(300, 420);
		game_over.setLocationRelativeTo(f);
		winner.setLocationRelativeTo(f);
		gun_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/diddy_gun.png"));
		bullet_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/bullet.png"));
		ball_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/ball.png"));

		gun = new JLabel();
		gun.setBackground(Color.RED);
		gun.setOpaque(true);
		Canvas c = new BouncingBall();
		f.setLayout(null);
		f.add(detail_panel);
		f.add(c);
		gun.setBounds(0, 312, 150, 80);
		Key_actions key_actions = new Key_actions();
		c.addKeyListener(key_actions);
		c.setBounds(0, 0, area_x, area_y);
		Bullet_remaining.setBackground(new Color(255, 255, 255));
		score.setBackground(new Color(255, 255, 255));
		score.setBackground(new Color(255, 255, 255));
		Next_target.setBackground(new Color(255, 255, 255));
		Level.setBackground(new Color(255, 255, 255));
		score.setOpaque(true);
		Listener l = new Listener();
		c.addMouseListener(l);
		img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/fondo_cide.png"));
		detail_panel.setBounds(30, 18, 720, 50);
		detail_panel.add(Bullet_remaining);
		Bullet_remaining.setBounds(10, 10, 100, 20);
		detail_panel.add(score);
		score.setBounds(220, 10, 100, 20);
		detail_panel.add(Next_target);
		Next_target.setBounds(420, 10, 100, 20);
		detail_panel.add(Level);
		Level.setBounds(610, 10, 100, 20);
		detail_panel.setBackground(new Color(255, 255, 255));

		Bullet_remaining.setFont(new Font("Fugaz one", 0, 17));
		score.setFont(new Font("Fugaz one", 0, 17));
		Next_target.setFont(new Font("Fugaz one", 0, 17));
		Level.setFont(new Font("Fugaz one", 0, 17));
		
		game_over.setResizable(false);
		winner.setResizable(false);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.getContentPane().setBackground(new Color(34, 177, 76));
	}

	public BouncingBall() {
		Thread t = new Thread(() -> {
			while (true) {
				if (move_ball) {
					if (level > 5 && won == false) {
						move_ball = false;
						bullet_fire = false;
						clip.stop();
						if (bgmusic) {
							try {
								BufferedInputStream audioFile = new BufferedInputStream(
										bBall.getResourceAsStream("/res/win.wav"));
								AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
								Clip hclip = AudioSystem.getClip();
								hclip.open(audioInputStream);
								hclip.start();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
						won = true;
						popup_msg = "GUANYADOR!";
						Level.setText("Nivell: 5");
						winner.setVisible(true);

					}
					if (up) {
						x++;
						if (x == 330) {
							up = false;
						}
					} else {
						x--;
						if (x == 0) {
							up = true;
						}
					}
				}
				try {
					Thread.sleep(ball_speed);
				} catch (Exception e) {
				}
				repaint();
			}
		});
		t.start();

		Thread bullet = new Thread(() -> {
			int newx;
			int random_value;
			int random_add_sub;
			while (bulletf) {
				// level
				if (point >= target && bullet_fire) {
					level += 1;
					level_changed = true;
					Random rand = new Random();

					// chnage x with level
					random_value = rand.nextInt(10) + 40;
					random_add_sub = rand.nextInt(2) + 1;
					if (random_add_sub < 0) {
						random_add_sub = -random_add_sub;
					}
					if (random_add_sub == 1) {

						if (random_value + ballx > 780) {
							newx = ballx + random_value;
							newx = newx - area_x;
							ballx += newx;
						} else {
							ballx += random_value;
						}
					} else {
						if (ballx - random_value < 250) {
							newx = ballx - random_value;
							newx = 240 - newx;
							ballx -= newx;
						} else {
							ballx -= random_value;
						}
					}
				}

				if (a < 700 && bullet_fire) {
					a = a + 5;
					int ball_outer_edge = ballx;
					int bullet_point = bulletx + 38;

					if (((bullet_point == ball_outer_edge)
							|| (bullet_point < ball_outer_edge + 10 && bullet_point > ball_outer_edge - 10))) {
						if (consecutive_hit == 2) {
							if (gamemusic) {
								try {
									BufferedInputStream audioFile = new BufferedInputStream(
											bBall.getResourceAsStream("/res/bonus.wav"));
									AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
									Clip hclip = AudioSystem.getClip();
									hclip.open(audioInputStream);
									hclip.start();
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
							popup_msg = "Bala +1";
							show_popup = true;
							consecutive_hit = 0;
							Bullet_count += 1;
							Bullet_remaining.setText("Bales: " + Bullet_count);
						}
						if (bullety - 15 == bally) {
							if (score_update) {
								score_update = false;
								point += 20;
								if (hit == true) {
									consecutive_hit += 1;
								}
								if (Bullet_count > 0) {
									move_ball = false;
								}
								hit = true;
								ball_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/explode.png"));
								if (gamemusic) {
									try {
										BufferedInputStream audioFile = new BufferedInputStream(
												bBall.getResourceAsStream("/res/hit.wav"));
										AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
										Clip hclip = AudioSystem.getClip();
										hclip.open(audioInputStream);
										hclip.start();
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
							}
							score.setText("Punts: " + point);

						} else if (bally <= bullety && bally >= bullety - 19) {
							if (score_update) {
								score_update = false;
								point += 10;
								if (hit == true) {
									consecutive_hit += 1;
								}
								if (Bullet_count > 0) {
									move_ball = false;
								}
								hit = true;
								if (gamemusic) {
									try {
										BufferedInputStream audioFile = new BufferedInputStream(
												bBall.getResourceAsStream("/res/hit.wav"));
										AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
										Clip hclip = AudioSystem.getClip();
										hclip.open(audioInputStream);
										hclip.start();
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
								ball_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/explode.png"));
							}
							score.setText("Punts: " + point);
						} else if (bally <= bullety - 21 && bally >= bullety - 35) {
							if (score_update) {
								score_update = false;
								point += 10;
								if (hit == true) {
									consecutive_hit += 1;
								}
								if (Bullet_count > 0) {
									move_ball = false;
								}
								hit = true;
								ball_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/explode.png"));
								if (gamemusic) {
									try {
										BufferedInputStream audioFile = new BufferedInputStream(
												bBall.getResourceAsStream("/res/hit.wav"));
										AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
										Clip hclip = AudioSystem.getClip();
										hclip.open(audioInputStream);
										hclip.start();
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
							}
							score.setText("Punts: " + point);

						} else {
							hit = false;
						}

					}

				}

				else {
					ball_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/ball.png"));
					score_update = true;
					bullet_fire = false;
					a = 0;
					if (Bullet_count <= 0 && point != target && consecutive_hit != 2) {

						clip.stop();
						if (move_ball) {
							move_ball = false;
							game_over.setVisible(true);
							bullet_fire = false;
							if (bgmusic) {
								try {
									BufferedInputStream audioFile = new BufferedInputStream(
											bBall.getResourceAsStream("/res/gameover.wav"));
									AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
									Clip hclip = AudioSystem.getClip();
									hclip.open(audioInputStream);
									hclip.start();
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					} else {
						move_ball = true;
					}
				}
				if (level_changed) {
					consecutive_hit = 0;
					popup_msg = "Nivell: " + level;
					show_popup = true;
					if (level == 2) {
						ball_speed -= 1;
						Bullet_count = 6;
						target += 30;
					} else if (level == 3) {
						ball_speed -= 1;
						Bullet_count = 5;
						target += 30;
					} else if (level == 4) {
						ball_speed -= 2;
						Bullet_count = 5;
						target += 40;
					} else if (level == 5) {
						ball_speed = 3;
						Bullet_count = 3;
						target += 30;
					}

					score.setText("Punts: " + point);
					Level.setText("Nivell: " + level);
					Next_target.setText("Objectiu: " + target);
					Bullet_remaining.setText("Bales: " + Bullet_count);
					level_changed = false;
				}
				try {
					Thread.sleep(3);
				} catch (Exception e) {
				}
				repaint();
			}
		});
		bullet.start();
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		bf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		try {
			animate(bf.getGraphics());
			g.drawImage(bf, 0, 0, null);
		} catch (Exception e) {
		}
	}

	public void animate(Graphics g) {
		super.paint(g);

		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		g.drawImage(gun_img, -80, 141, 380, 420, this);
		bally = 400 - x;

		g.setColor(Color.BLACK);
		g.setColor(Color.RED);
		g.drawImage(ball_img, ballx, bally, 50, 50, this);

		// Bullet
		if (bullet_fire) {
			bulletx = a + 230;
			bullety = 278;
			g.drawImage(bullet_img, bulletx, bullety, 30, 5, this);
		}
		g.setColor(Color.WHITE);
		g.fillRoundRect(20, 10, 750, 60, 20, 30);

		if (show_popup) {
			g.fillRoundRect(340, 150, 120, 50, 20, 30);
			g.setColor(Color.BLACK);
			g.setFont(pop_font);
			g.drawString(popup_msg, 357, 180);
		}

		if (startup_screen) {
			g.setColor(Color.WHITE);
			g.setFont(pop_font);
			g.drawString("Clicka per començar", 280, 530);
		}

	}
}