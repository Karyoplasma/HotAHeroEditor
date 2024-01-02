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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import actions.OpenExecutableButtonAction;
import core.Hero;
import core.SecondarySkill;
import core.Specialty;
import core.SpecialtyFactory;
import core.SpellBook;
import core.enums.Creature;
import core.enums.Gender;
import core.enums.HeroHeader;
import core.enums.HeroTrait;
import core.enums.Profession;
import core.enums.Race;
import core.enums.Resource;
import core.enums.SkillLevel;
import core.enums.Spell;
import core.enums.SpecialtyType;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

public class HotAHeroEditor extends Observable{

	private Path saveDirectory;
	private boolean isHotA;
	private Hero currentHero;
	private Map<String, Hero> originalHeroes;
	private List<Hero> heroes;
	private JFrame frame;
	private JCheckBox chckbxSaveDirectory;
	private JButton btnSave, btnLoad, btnDiscardAll,btnUnlock, btnWrite, btnChange;
	private JTable tableChanges;
	private JPanel panelSpecialtyOptions;
	private JComboBox<Resource> specialtyResources;
	private JSpinner specialtyAttack, specialtyDefense, specialtyDamage;
	private JComboBox<HeroTrait> specialtySkill, comboBoxFirstSkill, comboBoxSecondSkill;
	private JComboBox<Spell> specialtySpell, comboBoxSpell;
	private JComboBox<Creature> specialtyCreature, specialtyFirstConversion, specialtySecondConversion, comboBoxFirstTroop, comboBoxSecondTroop, comboBoxThirdTroop;
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
		this.heroes = new ArrayList<Hero>();
		this.currentHero = null;
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][][grow][][]", "[][][grow][][][][][grow][][][grow][]"));

		chckbxSaveDirectory = new JCheckBox("Save directory");
		chckbxSaveDirectory.setSelected(true);
		frame.getContentPane().add(chckbxSaveDirectory, "cell 1 0,grow");

		JButton btnOpenExecutable = new JButton(new OpenExecutableButtonAction(saveDirectory, this));
		btnOpenExecutable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnOpenExecutable, "cell 0 0,grow");

		btnUnlock = new JButton("Unlock");
		btnUnlock.setEnabled(false);
		btnUnlock.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(btnUnlock, "cell 3 0,grow");

		btnWrite = new JButton("Write");
		btnWrite.setEnabled(false);
		btnWrite.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(btnWrite, "cell 4 0,grow");
		
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
		
		panelSpecialtyOptions = new JPanel();
		frame.getContentPane().add(panelSpecialtyOptions, "cell 1 3 2 1,grow");
		panelSpecialtyOptions.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
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
		
		btnChange = new JButton("Change");
		btnChange.setEnabled(false);
		btnChange.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(btnChange, "cell 0 8 3 1,alignx center,growy");
		
		JPanel panelSeparator = new JPanel();
		frame.getContentPane().add(panelSeparator, "cell 0 9 5 1,grow");
		panelSeparator.setLayout(new BorderLayout(0, 0));

		JSeparator separator = new JSeparator();
		panelSeparator.add(separator, BorderLayout.CENTER);

		JScrollPane scrollPaneChanges = new JScrollPane();
		frame.getContentPane().add(scrollPaneChanges, "cell 0 10 5 1,grow");

		tableChanges = new JTable();
		//tableChanges.setModel(new ChangesTableModel(this));
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
		frame.getContentPane().add(btnDiscardAll, "cell 4 11");
		
		// TODO write and setup models
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

	public List<Hero> getHeroes() {
		return this.heroes;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public JTable getTableChanges() {
		return tableChanges;
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
	
	public void setCurrentHero(Hero currentHero) {
		this.currentHero = currentHero;
		setChanged();
		notifyObservers(this.currentHero);
	}
	
	public void resetHeroes(List<Hero> heroes) {
		this.heroes.clear();
		this.heroes.addAll(heroes);
		this.btnLoad.setEnabled(true);
		// initialize tables
		//((ChangesTableModel) this.tableChanges.getModel()).initializeData();	
		//writeListToDisk(heroes);
	}
	
	public void readOriginalHeroes() {
		this.originalHeroes = new HashMap<String, Hero>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("resources/originalHeroes"));
			String in;
			while((in = reader.readLine()) != null) {
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
				SecondarySkill secondary1 = new SecondarySkill(HeroTrait.values()[Integer.parseInt(secondaryArr[0])], SkillLevel.values()[Integer.parseInt(secondaryArr[1])]);
				secondaryArr = inSplit[6].split(",");
				SecondarySkill secondary2 = new SecondarySkill(HeroTrait.values()[Integer.parseInt(secondaryArr[0])], SkillLevel.values()[Integer.parseInt(secondaryArr[1])]);
				SpellBook spellBook = new SpellBook(Spell.values()[Integer.parseInt(inSplit[7])]);
				String[] troopsArr = inSplit[8].split(",");
				Creature[] startingTroops = new Creature[3];
				for (int i = 0; i < 3; i++) {
					startingTroops[i] = Creature.values()[Integer.parseInt(troopsArr[i])];
				}
				Hero hero = new Hero(header, gender, race, profession, specialty, secondary1, secondary2, spellBook, startingTroops);
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

