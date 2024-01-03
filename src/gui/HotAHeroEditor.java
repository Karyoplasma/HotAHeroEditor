package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import actions.HeroComboBoxItemListener;
import actions.HeroTraitComboBoxListener;
import actions.OpenExecutableButtonAction;
import actions.SpecialtyComboBoxListener;
import actions.ChangeButtonAction;
import core.Hero;
import core.SecondarySkill;
import core.Specialty;
import core.SpecialtyFactory;
import core.SpellBook;
import core.enums.*;
import models.*;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Component;

public class HotAHeroEditor {

	private Path saveDirectory;
	private boolean isHotA;
	private Map<String, Hero> originalHeroes;
	private JFrame frame;
	private JCheckBox chckbxSaveDirectory;
	private JButton btnSave, btnLoad, btnDiscardAll, btnUnlock, btnWrite, btnChange;
	private JTable tableChanges;
	private JPanel panelSpecialtyOptions;
	private JComboBox<Resource> specialtyResources;
	private JSpinner specialtyCreatureAttack, specialtyCreatureDefense, specialtyCreatureDamage, specialtyDragonAttack,
			specialtyDragonDefense, specialtySpeed;
	private JComboBox<HeroTrait> specialtySkill, comboBoxFirstSkill, comboBoxSecondSkill;
	private JComboBox<Spell> specialtySpell, comboBoxSpell;
	private JComboBox<Creature> specialtyCreature, specialtyCreatureStatic, specialtyFirstConversion,
			specialtySecondConversion, specialtyConversionResult, comboBoxFirstTroop, comboBoxSecondTroop,
			comboBoxThirdTroop;
	private JComboBox<Hero> comboBoxHero;
	private JComboBox<SpecialtyType> comboBoxSpecialty;
	private JComboBox<SkillLevel> comboBoxFirstSkillLevel, comboBoxSecondSkillLevel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HotAHeroEditor window = new HotAHeroEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HotAHeroEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.initializeSaveDirectory();
		this.readOriginalHeroes();
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][][350px:n][grow][][]", "[][][][][][][][grow][][][grow][]"));

		chckbxSaveDirectory = new JCheckBox("Save directory");
		chckbxSaveDirectory.setSelected(true);
		frame.getContentPane().add(chckbxSaveDirectory, "cell 1 0,grow");

		JButton btnOpenExecutable = new JButton(new OpenExecutableButtonAction(saveDirectory, this));
		btnOpenExecutable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnOpenExecutable, "cell 0 0,grow");

		btnUnlock = new JButton("Unlock");
		btnUnlock.setEnabled(false);
		btnUnlock.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(btnUnlock, "cell 4 0,grow");

		btnWrite = new JButton("Write");
		btnWrite.setEnabled(false);
		btnWrite.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(btnWrite, "cell 5 0,grow");

		comboBoxHero = new JComboBox<Hero>();
		comboBoxHero.setEnabled(false);
		comboBoxHero.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(comboBoxHero, "cell 0 1,grow");

		JLabel lblSpecialty = new JLabel("Specialty:");
		lblSpecialty.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(lblSpecialty, "cell 0 2,alignx trailing,growy");

		comboBoxSpecialty = new JComboBox<SpecialtyType>();
		comboBoxSpecialty.setEnabled(false);
		comboBoxSpecialty.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(comboBoxSpecialty, "cell 1 2,growx,aligny center");

		specialtyCreatureAttack = new JSpinner(new SpinnerNumberModel(0,0, Integer.MAX_VALUE, 1));
		specialtyCreatureAttack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyCreatureDefense = new JSpinner(new SpinnerNumberModel(0,0, Integer.MAX_VALUE, 1));
		specialtyCreatureDefense.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyCreatureDamage = new JSpinner(new SpinnerNumberModel(0,0, Integer.MAX_VALUE, 1));
		specialtyCreatureDamage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyDragonAttack = new JSpinner(new SpinnerNumberModel(0,0, Integer.MAX_VALUE, 1));
		specialtyDragonAttack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyDragonDefense = new JSpinner(new SpinnerNumberModel(0,0, Integer.MAX_VALUE, 1));
		specialtyDragonDefense.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtySpeed = new JSpinner(new SpinnerNumberModel(0,0, Integer.MAX_VALUE, 1));
		specialtySpeed.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyCreature = new JComboBox<Creature>();
		specialtyCreature.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyCreatureStatic = new JComboBox<Creature>();
		specialtyCreatureStatic.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyFirstConversion = new JComboBox<Creature>();
		specialtyFirstConversion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtySecondConversion = new JComboBox<Creature>();
		specialtySecondConversion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyConversionResult = new JComboBox<Creature>();
		specialtyConversionResult.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyResources = new JComboBox<Resource>();
		specialtyResources.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtySkill = new JComboBox<HeroTrait>();
		specialtySkill.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtySpell = new JComboBox<Spell>();
		specialtySpell.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelSpecialtyOptions = new JPanel();
		panelSpecialtyOptions.setLayout(new CardLayout());
		panelSpecialtyOptions.add(this.createEmptyPanel(), "Empty");
		panelSpecialtyOptions.add(this.createCreatureSpecialtyPanel(), "Creature");
		panelSpecialtyOptions.add(this.createSkillSpecialtyPanel(), "Skill");
		panelSpecialtyOptions.add(this.createResourceSpecialtyPanel(), "Resource");
		panelSpecialtyOptions.add(this.createSpellSpecialtyPanel(), "Spell");
		panelSpecialtyOptions.add(this.createStaticCreatureSpecialtyPanel(), "Static");
		panelSpecialtyOptions.add(this.createCreatureSpeedSpecialtyPanel(), "Speed");
		panelSpecialtyOptions.add(this.createCreatureConversionSpecialtyPanel(), "Conversion");
		panelSpecialtyOptions.add(this.createDragonSpecialtyPanel(), "Dragon");
		frame.getContentPane().add(panelSpecialtyOptions, "cell 1 3 2 1,grow");

		JLabel lblFirstSkill = new JLabel("First Skill:");
		lblFirstSkill.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFirstSkill.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(lblFirstSkill, "cell 0 4,alignx trailing,growy");

		comboBoxFirstSkillLevel = new JComboBox<SkillLevel>();
		comboBoxFirstSkillLevel.setEnabled(false);
		comboBoxFirstSkillLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(comboBoxFirstSkillLevel, "cell 1 4,growx,aligny center");

		comboBoxFirstSkill = new JComboBox<HeroTrait>();
		comboBoxFirstSkill.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxFirstSkill.setEnabled(false);
		frame.getContentPane().add(comboBoxFirstSkill, "cell 2 4,growx,aligny center");

		JLabel lblSecondSkill = new JLabel("Second Skill:");
		lblSecondSkill.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(lblSecondSkill, "cell 0 5,alignx trailing,growy");

		comboBoxSecondSkillLevel = new JComboBox<SkillLevel>();
		comboBoxSecondSkillLevel.setEnabled(false);
		comboBoxSecondSkillLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(comboBoxSecondSkillLevel, "cell 1 5,growx,aligny center");

		comboBoxSecondSkill = new JComboBox<HeroTrait>();
		comboBoxSecondSkill.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxSecondSkill.setEnabled(false);
		frame.getContentPane().add(comboBoxSecondSkill, "cell 2 5,growx,aligny center");

		JLabel lblStartingSpell = new JLabel("Starting Spell:");
		lblStartingSpell.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(lblStartingSpell, "cell 0 6,alignx trailing,growy");

		comboBoxSpell = new JComboBox<Spell>();
		comboBoxSpell.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxSpell.setEnabled(false);
		frame.getContentPane().add(comboBoxSpell, "cell 1 6,growx,aligny center");

		JLabel lblStartingTroops = new JLabel("Starting Troops:");
		lblStartingTroops.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(lblStartingTroops, "cell 0 7,alignx trailing,growy");

		JPanel panelStartingTroops = new JPanel();
		frame.getContentPane().add(panelStartingTroops, "cell 1 7 2 1,grow");
		panelStartingTroops.setLayout(new BorderLayout());

		comboBoxFirstTroop = new JComboBox<Creature>();
		comboBoxFirstTroop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxFirstTroop.setEnabled(false);
		panelStartingTroops.add(comboBoxFirstTroop, BorderLayout.NORTH);

		comboBoxSecondTroop = new JComboBox<Creature>();
		comboBoxSecondTroop.setEnabled(false);
		comboBoxSecondTroop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelStartingTroops.add(comboBoxSecondTroop, BorderLayout.CENTER);

		comboBoxThirdTroop = new JComboBox<Creature>();
		comboBoxThirdTroop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxThirdTroop.setEnabled(false);
		panelStartingTroops.add(comboBoxThirdTroop, BorderLayout.SOUTH);

		btnChange = new JButton(new ChangeButtonAction(this));
		btnChange.setEnabled(false);
		btnChange.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(btnChange, "cell 0 8 4 1,alignx center,growy");

		JPanel panelSeparator = new JPanel();
		frame.getContentPane().add(panelSeparator, "cell 0 9 6 1,grow");
		panelSeparator.setLayout(new BorderLayout(0, 0));

		JSeparator separator = new JSeparator();
		panelSeparator.add(separator, BorderLayout.CENTER);

		JScrollPane scrollPaneChanges = new JScrollPane();
		frame.getContentPane().add(scrollPaneChanges, "cell 0 10 6 1,grow");

		tableChanges = new JTable();
		tableChanges.setShowVerticalLines(false);
		tableChanges.setShowHorizontalLines(false);
		tableChanges.setShowGrid(false);
		tableChanges.setFillsViewportHeight(true);
		tableChanges.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPaneChanges.setViewportView(tableChanges);

		btnLoad = new JButton("load");
		btnLoad.setEnabled(false);
		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnLoad, "cell 0 11,grow");

		btnSave = new JButton("save");
		btnSave.setEnabled(false);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnSave, "cell 1 11,grow");

		btnDiscardAll = new JButton("Discard All");
		btnDiscardAll.setEnabled(false);
		btnDiscardAll.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnDiscardAll, "cell 5 11");

	}

	private Component createCreatureSpecialtyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "[grow]", "[]"));
		panel.add(specialtyCreature, "cell 0 0,growx,aligny top");
		return panel;
	}

	private JPanel createSpellSpecialtyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "[grow]", "[]"));
		panel.add(specialtySpell, "cell 0 0,growx,aligny top");
		return panel;
	}

	private JPanel createCreatureConversionSpecialtyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "[grow][][grow][][grow]", "[]"));
		JLabel lblOr = new JLabel("OR");
		lblOr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblOr, "cell 1 0,alignx center,aligny center");
		JLabel lblTo = new JLabel("-->");
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblTo, "cell 3 0,alignx center,aligny center");
		panel.add(specialtyFirstConversion, "cell 0 0,growx,aligny top");
		panel.add(specialtySecondConversion, "cell 2 0,growx,aligny top");
		panel.add(specialtyConversionResult, "cell 4 0,growx,aligny top");
		return panel;
	}

	private JPanel createCreatureSpeedSpecialtyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "[][grow]", "[]"));
		JLabel lblSpeedBonus = new JLabel("Speed Bonus:");
		lblSpeedBonus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblSpeedBonus, "cell 0 0,alignx right,aligny top");
		panel.add(specialtySpeed,"cell 1 0,growx,aligny top");
		return panel;
	}

	private JPanel createStaticCreatureSpecialtyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "[grow][][grow][][grow][][grow]", "[]"));
		JLabel lblAttack = new JLabel("Attack:");
		lblAttack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblAttack, "cell 1 0,alignx right,aligny top");
		JLabel lblDefense = new JLabel("Defense:");
		lblDefense.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblDefense, "cell 3 0,alignx right,aligny top");
		JLabel lblDamage = new JLabel("Damage:");
		lblDamage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblDamage, "cell 5 0,alignx right,aligny top");
		panel.add(specialtyCreatureStatic, "cell 0 0,growx,aligny top");
		panel.add(specialtyCreatureAttack, "cell 2 0,growx,aligny top");
		panel.add(specialtyCreatureDefense, "cell 4 0,growx,aligny top");
		panel.add(specialtyCreatureDamage, "cell 6 0,growx,aligny top");
		return panel;
	}

	private JPanel createResourceSpecialtyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "[grow]", "[]"));
		panel.add(specialtyResources, "cell 0 0,growx,aligny top");
		return panel;
	}

	private JPanel createEmptyPanel() {
		JPanel panel = new JPanel();
		return panel;
	}

	private JPanel createDragonSpecialtyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "[][grow][][grow]", "[]"));
		JLabel lblAttack = new JLabel("Attack:");
		lblAttack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblAttack, "cell 0 0,alignx right,aligny top");
		JLabel lblNewLabel = new JLabel("Defense:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblNewLabel, "cell 2 0,alignx right,aligny top");
		panel.add(specialtyDragonAttack, "cell 1 0,growx,aligny top");
		panel.add(specialtyDragonDefense, "cell 3 0,growx,aligny top");
		return panel;
	}

	private JPanel createSkillSpecialtyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "[grow]", "[]"));
		panel.add(specialtySkill, "cell 0 0,growx,aligny top");
		return panel;
	}

	private void initializeSaveDirectory() {
		try (BufferedReader reader = new BufferedReader(new FileReader("resources/directoryPath.txt"))) {
			this.saveDirectory = Paths.get(reader.readLine());
		} catch (IOException e) {
			// ignore error
		}

	}

	public boolean savePathPreference() {
		return this.chckbxSaveDirectory.isSelected();
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public JButton getBtnSave() {
		return btnSave;
	}

	public JButton getBtnLoad() {
		return btnLoad;
	}

	public JButton getBtnDiscardAll() {
		return btnDiscardAll;
	}

	public JButton getBtnUnlock() {
		return btnUnlock;
	}

	public JButton getBtnWrite() {
		return btnWrite;
	}

	public JButton getBtnChange() {
		return btnChange;
	}

	public JComboBox<HeroTrait> getComboBoxFirstSkill() {
		return comboBoxFirstSkill;
	}

	public JComboBox<HeroTrait> getComboBoxSecondSkill() {
		return comboBoxSecondSkill;
	}

	public JComboBox<Spell> getComboBoxSpell() {
		return comboBoxSpell;
	}

	public JComboBox<Creature> getComboBoxFirstTroop() {
		return comboBoxFirstTroop;
	}

	public JComboBox<Creature> getComboBoxSecondTroop() {
		return comboBoxSecondTroop;
	}

	public JComboBox<Creature> getComboBoxThirdTroop() {
		return comboBoxThirdTroop;
	}

	public JComboBox<Hero> getComboBoxHero() {
		return comboBoxHero;
	}

	public JComboBox<SpecialtyType> getComboBoxSpecialty() {
		return comboBoxSpecialty;
	}

	public JComboBox<SkillLevel> getComboBoxFirstSkillLevel() {
		return comboBoxFirstSkillLevel;
	}

	public JComboBox<SkillLevel> getComboBoxSecondSkillLevel() {
		return comboBoxSecondSkillLevel;
	}

	public JComboBox<Resource> getSpecialtyResources() {
		return specialtyResources;
	}
	
	public JSpinner getSpecialtyCreatureAttack() {
		return specialtyCreatureAttack;
	}

	public JSpinner getSpecialtyCreatureDefense() {
		return specialtyCreatureDefense;
	}

	public JSpinner getSpecialtyCreatureDamage() {
		return specialtyCreatureDamage;
	}
	
	public JSpinner getSpecialtyDragonAttack() {
		return specialtyDragonAttack;
	}
	public JSpinner getSpecialtyDragonDefense() {
		return specialtyDragonDefense;
	}
	public JSpinner getSpecialtySpeed() {
		return specialtySpeed;
	}

	public JComboBox<HeroTrait> getSpecialtySkill() {
		return specialtySkill;
	}

	public JComboBox<Spell> getSpecialtySpell() {
		return specialtySpell;
	}

	public JComboBox<Creature> getSpecialtyCreature() {
		return specialtyCreature;
	}

	public JComboBox<Creature> getSpecialtyFirstConversion() {
		return specialtyFirstConversion;
	}

	public JComboBox<Creature> getSpecialtySecondConversion() {
		return specialtySecondConversion;
	}

	public JComboBox<Creature> getSpecialtyConversionResult() {
		return specialtyConversionResult;
	}
	
	public JComboBox<Creature> getSpecialtyCreatureStatic(){
		return specialtyCreatureStatic;
	}
	
	public JTable getTableChanges() {
		return tableChanges;
	}

	public JPanel getSpecialtyOptionsPanel() {
		return panelSpecialtyOptions;
	}

	public boolean isHotA() {
		return isHotA;
	}

	public Hero getOriginalHero(String key) {
		return this.originalHeroes.getOrDefault(key, null);
	}

	public void setIsHotA(boolean isHotA) {
		this.isHotA = isHotA;
	}

	public void setSpecialtyOptionsPanel(JPanel specialtyOptions) {
		this.panelSpecialtyOptions = specialtyOptions;
		this.frame.repaint();
	}

	public void resetHeroes(List<Hero> heroes) {
		// initialize components
		this.comboBoxFirstSkillLevel.setModel(new SkillLevelComboBoxModel());
		this.comboBoxFirstSkillLevel.setEnabled(true);
		this.comboBoxSecondSkillLevel.setModel(new SkillLevelComboBoxModel());
		this.comboBoxSecondSkillLevel.setEnabled(true);
		this.comboBoxFirstSkill.setModel(new HeroTraitComboBoxModel(isHotA, false));
		this.comboBoxFirstSkill.setEnabled(true);
		this.comboBoxSecondSkill.setModel(new HeroTraitComboBoxModel(isHotA, true));
		this.comboBoxSecondSkill.setEnabled(true);
		this.comboBoxSecondSkill.addItemListener(new HeroTraitComboBoxListener(this));
		this.comboBoxSpell.setModel(new SpellComboBoxModel());
		this.comboBoxSpell.setEnabled(true);
		this.comboBoxFirstTroop.setModel(new CreatureComboBoxModel(isHotA, false));
		this.comboBoxFirstTroop.setEnabled(true);
		this.comboBoxSecondTroop.setModel(new CreatureComboBoxModel(isHotA, true));
		this.comboBoxSecondTroop.setEnabled(true);
		this.comboBoxThirdTroop.setModel(new CreatureComboBoxModel(isHotA, false));
		this.comboBoxThirdTroop.setEnabled(true);
		this.specialtyCreature.setModel(new CreatureComboBoxModel(isHotA, true));
		this.specialtyCreatureStatic.setModel(new CreatureComboBoxModel(isHotA, true));
		this.specialtyConversionResult.setModel(new CreatureComboBoxModel(isHotA, false));
		this.specialtyFirstConversion.setModel(new CreatureComboBoxModel(isHotA, false));
		this.specialtySecondConversion.setModel(new CreatureComboBoxModel(isHotA, false));
		this.specialtySpell.setModel(new SpellSpecialtyComboBoxModel());
		this.specialtyResources.setModel(new ResourceComboBoxModel());
		this.specialtySkill.setModel(new HeroTraitComboBoxModel(isHotA, false));
		this.comboBoxSpecialty.setModel(new SpecialtyComboBoxModel(isHotA));
		this.comboBoxSpecialty.addItemListener(new SpecialtyComboBoxListener(this));
		this.comboBoxSpecialty.setEnabled(true);
		this.comboBoxHero.setModel(new HeroComboBoxModel(heroes));
		this.comboBoxHero.addItemListener(new HeroComboBoxItemListener(this));
		this.comboBoxHero.setEnabled(true);
		this.comboBoxHero.setSelectedItem(heroes.get(0));
		// initialize table
		this.tableChanges.setModel(new ChangesTableModel(this, heroes));
	}

	public void readOriginalHeroes() {
		this.originalHeroes = new HashMap<String, Hero>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("resources/originalHeroes"));
			String in;
			while ((in = reader.readLine()) != null) {
				String[] inSplit = in.split(";");
				HeroHeader header = HeroHeader.values()[Integer.parseInt(inSplit[0])];
				Gender gender = Gender.values()[Integer.parseInt(inSplit[1])];
				Race race = Race.values()[Integer.parseInt(inSplit[2])];
				Profession profession = Profession.values()[Integer.parseInt(inSplit[3])];
				ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
				String[] specialtyArr = inSplit[4].split(",");
				for (int i = 0; i < 7; i++) {
					buffer.putInt(Integer.parseInt(specialtyArr[i]));
				}
				buffer.flip();
				Specialty specialty = SpecialtyFactory.createSpecialtyFromBuffer(buffer);
				String[] secondaryArr = inSplit[5].split(",");
				SecondarySkill secondary1 = new SecondarySkill(HeroTrait.values()[Integer.parseInt(secondaryArr[0])],
						SkillLevel.values()[Integer.parseInt(secondaryArr[1])]);
				secondaryArr = inSplit[6].split(",");
				SecondarySkill secondary2 = new SecondarySkill(HeroTrait.values()[Integer.parseInt(secondaryArr[0])],
						SkillLevel.values()[Integer.parseInt(secondaryArr[1])]);
				SpellBook spellBook = new SpellBook(Spell.values()[Integer.parseInt(inSplit[7])]);
				String[] troopsArr = inSplit[8].split(",");
				Creature[] startingTroops = new Creature[3];
				for (int i = 0; i < 3; i++) {
					startingTroops[i] = Creature.values()[Integer.parseInt(troopsArr[i])];
				}
				Hero hero = new Hero(header, gender, race, profession, specialty, secondary1, secondary2, spellBook,
						startingTroops);
				this.originalHeroes.put(header.toString(), hero);
				if (hero.isChanged(hero)) {
					hero.debug();
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public static void writeListToDisk(List<Hero> list) {
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/originalHeroes"));
//            //HeroHeader header, Gender gender, Race race, Profession profession, Specialty specialty,
//			//SecondarySkill secondary1, SecondarySkill secondary2, SpellBook spellBook, Creature[] startingTroops
//            for (Hero hero : list) {
//            	writer.write(Integer.toString(hero.getHeader().ordinal()));
//            	writer.write(";");
//            	writer.write(Integer.toString(hero.getGender().ordinal()));
//            	writer.write(";");
//            	writer.write(Integer.toString(hero.getRace().ordinal()));
//            	writer.write(";");
//            	writer.write(Integer.toString(hero.getProfession().ordinal()));
//            	writer.write(";");
//            	ByteBuffer buffer = hero.getSpecialty().getByteBuffer();
//            	for (int i = 0; i < 7; i++) {
//            		writer.write(Integer.toString(buffer.getInt()));
//            		if (i == 6) {
//            			writer.write(";");
//            		} else {
//            			writer.write(",");
//            		}
//            	}
//            	writer.write(Integer.toString(hero.getSecondary1().getTrait().ordinal()));
//            	writer.write(",");
//            	writer.write(Integer.toString(hero.getSecondary1().getLevel().ordinal()));
//            	writer.write(";");
//            	writer.write(Integer.toString(hero.getSecondary2().getTrait().ordinal()));
//            	writer.write(",");
//            	writer.write(Integer.toString(hero.getSecondary2().getLevel().ordinal()));
//            	writer.write(";");
//            	writer.write(Integer.toString(hero.getSpellBook().getSpell().ordinal()));
//            	writer.write(";");
//            	for (int i = 0; i < 3; i++) {
//            		writer.write(Integer.toString(hero.getStartingTroops()[i].ordinal()));
//            		if (i == 2) {
//            			writer.newLine();
//            		} else {
//            			writer.write(",");
//            		}
//            	}
//            }
//            writer.close();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }
}
