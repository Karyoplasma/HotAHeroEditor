package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import actions.ChangeButtonAction;
import actions.DiscardAllButtonAction;
import actions.HeroComboBoxItemListener;
import actions.HeroTraitComboBoxListener;
import actions.LoadButtonAction;
import actions.OpenExecutableButtonAction;
import actions.ExportButtonAction;
import actions.SpecialtyComboBoxListener;
import actions.UnlockButtonAction;
import actions.WriteButtonAction;
import actions.OpenGameDirectoryMenuAction;
import actions.OpenModFolderMenuAction;
import core.Hero;
import core.ModFileHandler;
import core.enums.Creature;
import core.enums.HeroTrait;
import core.enums.Resource;
import core.enums.SkillLevel;
import core.enums.SpecialtyType;
import core.enums.Spell;
import models.ChangesTableModel;
import models.CreatureComboBoxModel;
import models.CreatureSpecialtyComboBoxModel;
import models.HeroComboBoxModel;
import models.HeroTraitComboBoxModel;
import models.ResourceComboBoxModel;
import models.SkillLevelComboBoxModel;
import models.SpecialtyComboBoxModel;
import models.SpellComboBoxModel;
import models.SpellSpecialtyComboBoxModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class HotAHeroEditor {
    
	private static final Logger logger = LogManager.getLogger(HotAHeroEditor.class);

	private static int totalHeroes = 156;
	private static int totalCreatures = 146;
	private Path saveDirectory;
	private boolean isHotA;
	private Map<String, Hero> originalHeroes;
	private JFrame frmHommHeroEditor;
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
	private Path executable;
	private JMenuBar menuBar;
	private JMenu mnActions;
	private JMenuItem mntmOpenGameDir;
	private JMenuItem mntmOpenModFolder;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HotAHeroEditor window = new HotAHeroEditor();
					window.frmHommHeroEditor.setVisible(true);
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
		logger.info("Initializing done.");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		logger.info("Initializing...");
		frmHommHeroEditor = new JFrame();
		frmHommHeroEditor.setTitle("HoMM3 Hero Editor");
		frmHommHeroEditor.setBounds(100, 100, 800, 600);
		frmHommHeroEditor.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmHommHeroEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHommHeroEditor.getContentPane()
				.setLayout(new MigLayout("", "[][][350px:n][grow][][]", "[][][][][][][][grow][][][grow][]"));
		
		this.initializeSaveDirectory();
		this.originalHeroes = ModFileHandler.readOriginalHeroes();
		if (this.originalHeroes == null) {
			JOptionPane.showMessageDialog(this.frmHommHeroEditor,
					"There was an error while reading the original heroes.\nPlease restart the program to fix this issue!",
					"Fatal Error", JOptionPane.ERROR_MESSAGE);
		}
		
		chckbxSaveDirectory = new JCheckBox("Save directory");
		chckbxSaveDirectory.setSelected(true);
		frmHommHeroEditor.getContentPane().add(chckbxSaveDirectory, "cell 1 0,grow");

		JButton btnOpenExecutable = new JButton(new OpenExecutableButtonAction(saveDirectory, this));
		btnOpenExecutable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(btnOpenExecutable, "cell 0 0,grow");

		btnUnlock = new JButton(new UnlockButtonAction(this));
		btnUnlock.setEnabled(false);
		btnUnlock.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmHommHeroEditor.getContentPane().add(btnUnlock, "cell 4 0,grow");

		btnWrite = new JButton(new WriteButtonAction(this));
		btnWrite.setEnabled(false);
		btnWrite.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmHommHeroEditor.getContentPane().add(btnWrite, "cell 5 0,grow");

		comboBoxHero = new JComboBox<Hero>();
		comboBoxHero.setEnabled(false);
		comboBoxHero.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(comboBoxHero, "cell 0 1,grow");

		JLabel lblSpecialty = new JLabel("Specialty:");
		lblSpecialty.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(lblSpecialty, "cell 0 2,alignx trailing,growy");

		comboBoxSpecialty = new JComboBox<SpecialtyType>();
		comboBoxSpecialty.setEnabled(false);
		comboBoxSpecialty.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(comboBoxSpecialty, "cell 1 2,growx,aligny center");
		
		specialtyCreatureAttack = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		specialtyCreatureAttack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyCreatureDefense = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		specialtyCreatureDefense.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyCreatureDamage = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		specialtyCreatureDamage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyDragonAttack = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		specialtyDragonAttack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtyDragonDefense = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		specialtyDragonDefense.setFont(new Font("Tahoma", Font.PLAIN, 14));
		specialtySpeed = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
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
		frmHommHeroEditor.getContentPane().add(panelSpecialtyOptions, "cell 1 3 2 1,grow");

		JLabel lblFirstSkill = new JLabel("First Skill:");
		lblFirstSkill.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFirstSkill.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(lblFirstSkill, "cell 0 4,alignx trailing,growy");

		comboBoxFirstSkillLevel = new JComboBox<SkillLevel>();
		comboBoxFirstSkillLevel.setEnabled(false);
		comboBoxFirstSkillLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(comboBoxFirstSkillLevel, "cell 1 4,growx,aligny center");

		comboBoxFirstSkill = new JComboBox<HeroTrait>();
		comboBoxFirstSkill.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxFirstSkill.setEnabled(false);
		frmHommHeroEditor.getContentPane().add(comboBoxFirstSkill, "cell 2 4,growx,aligny center");

		JLabel lblSecondSkill = new JLabel("Second Skill:");
		lblSecondSkill.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(lblSecondSkill, "cell 0 5,alignx trailing,growy");

		comboBoxSecondSkillLevel = new JComboBox<SkillLevel>();
		comboBoxSecondSkillLevel.setEnabled(false);
		comboBoxSecondSkillLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(comboBoxSecondSkillLevel, "cell 1 5,growx,aligny center");

		comboBoxSecondSkill = new JComboBox<HeroTrait>();
		comboBoxSecondSkill.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxSecondSkill.setEnabled(false);
		frmHommHeroEditor.getContentPane().add(comboBoxSecondSkill, "cell 2 5,growx,aligny center");

		JLabel lblStartingSpell = new JLabel("Starting Spell:");
		lblStartingSpell.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(lblStartingSpell, "cell 0 6,alignx trailing,growy");

		comboBoxSpell = new JComboBox<Spell>();
		comboBoxSpell.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxSpell.setEnabled(false);
		frmHommHeroEditor.getContentPane().add(comboBoxSpell, "cell 1 6,growx,aligny center");

		JLabel lblStartingTroops = new JLabel("Starting Troops:");
		lblStartingTroops.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(lblStartingTroops, "cell 0 7,alignx trailing,aligny center");

		JPanel panelStartingTroops = new JPanel();
		frmHommHeroEditor.getContentPane().add(panelStartingTroops, "cell 1 7 2 1,growx,aligny center");
		panelStartingTroops.setLayout(new MigLayout("", "[grow]", "[][][]"));

		comboBoxFirstTroop = new JComboBox<Creature>();
		comboBoxFirstTroop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxFirstTroop.setEnabled(false);
		panelStartingTroops.add(comboBoxFirstTroop, "cell 0 0 1 1,grow");

		comboBoxSecondTroop = new JComboBox<Creature>();
		comboBoxSecondTroop.setEnabled(false);
		comboBoxSecondTroop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelStartingTroops.add(comboBoxSecondTroop, "cell 0 1 1 1,grow");

		comboBoxThirdTroop = new JComboBox<Creature>();
		comboBoxThirdTroop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxThirdTroop.setEnabled(false);
		panelStartingTroops.add(comboBoxThirdTroop, "cell 0 2 1 1,grow");

		btnChange = new JButton(new ChangeButtonAction(this));
		btnChange.setEnabled(false);
		btnChange.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmHommHeroEditor.getContentPane().add(btnChange, "cell 0 8 4 1,alignx center,growy");

		JPanel panelSeparator = new JPanel();
		frmHommHeroEditor.getContentPane().add(panelSeparator, "cell 0 9 6 1,grow");
		panelSeparator.setLayout(new BorderLayout(0, 0));

		JSeparator separator = new JSeparator();
		panelSeparator.add(separator, BorderLayout.CENTER);

		JScrollPane scrollPaneChanges = new JScrollPane();
		frmHommHeroEditor.getContentPane().add(scrollPaneChanges, "cell 0 10 6 1,grow");

		tableChanges = new JTable();
		tableChanges.setShowVerticalLines(false);
		tableChanges.setShowHorizontalLines(false);
		tableChanges.setShowGrid(false);
		tableChanges.setFillsViewportHeight(true);
		tableChanges.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPaneChanges.setViewportView(tableChanges);

		btnLoad = new JButton(new LoadButtonAction(this));
		btnLoad.setEnabled(false);
		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(btnLoad, "cell 0 11,grow");

		btnSave = new JButton(new ExportButtonAction(this));
		btnSave.setEnabled(false);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(btnSave, "cell 1 11,grow");

		btnDiscardAll = new JButton(new DiscardAllButtonAction(this));
		btnDiscardAll.setEnabled(false);
		btnDiscardAll.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmHommHeroEditor.getContentPane().add(btnDiscardAll, "cell 5 11");
		
		menuBar = new JMenuBar();
		frmHommHeroEditor.setJMenuBar(menuBar);
		
		mnActions = new JMenu("Shortcuts");
		menuBar.add(mnActions);
		
		mntmOpenGameDir = new JMenuItem(new OpenGameDirectoryMenuAction(this));
		mnActions.add(mntmOpenGameDir);
		
		mntmOpenModFolder = new JMenuItem(new OpenModFolderMenuAction());
		mnActions.add(mntmOpenModFolder);
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
		panel.add(specialtySpeed, "cell 1 0,growx,aligny top");
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
		logger.info("Reading save directory...");
		try (BufferedReader reader = new BufferedReader(new FileReader("resources/directoryPath.txt"))) {
			this.saveDirectory = Paths.get(reader.readLine());
		} catch (IOException e) {
			logger.error("Exception while reading save directory:", e);
		}

	}

	public boolean savePathPreference() {
		return this.chckbxSaveDirectory.isSelected();
	}

	public JFrame getFrame() {
		return this.frmHommHeroEditor;
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
	
	public Path getSaveDirectory() {
		return this.saveDirectory;
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

	public JComboBox<Creature> getSpecialtyCreatureStatic() {
		return specialtyCreatureStatic;
	}

	public JTable getTableChanges() {
		return tableChanges;
	}
	
	public int getTotalHeroes() {
		return totalHeroes;
	}
	
	public int getTotalCreatures() {
		return totalCreatures;
	}
	
	public JPanel getSpecialtyOptionsPanel() {
		return panelSpecialtyOptions;
	}

	public Path getExecutable() {
		return this.executable;
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
	
	public void setExecutable(Path executable) {
		this.executable = executable;
	}
	
	public void setSpecialtyOptionsPanel(JPanel specialtyOptions) {
		this.panelSpecialtyOptions = specialtyOptions;
		this.frmHommHeroEditor.repaint();
	}
	
	public void setSaveDirectory(Path saveDirectory) {
		this.saveDirectory = saveDirectory;
	}
	
	public static void setTotalCreatures(int creatures) {
		totalCreatures = creatures;
	}
	
	public static void setTotalHeroes(int heroes) {
		totalHeroes = heroes;
	}
	
	public void forceHeroBoxItemEventTrigger(Hero hero) {
		if (!comboBoxHero.getSelectedItem().equals(hero)) {
			comboBoxHero.setSelectedItem(hero);
			return;
		}
		ItemEvent event = new ItemEvent(comboBoxHero, ItemEvent.SELECTED, hero, ItemEvent.SELECTED);
		for (ItemListener listener : comboBoxHero.getItemListeners()) {
		    listener.itemStateChanged(event);
		}
	}
	
	public void resetHeroes(List<Hero> heroes) {
		logger.info("Initializing components...");
		// initialize components
		this.comboBoxFirstSkillLevel.setModel(new SkillLevelComboBoxModel());
		this.comboBoxFirstSkillLevel.setEnabled(true);
		this.comboBoxSecondSkillLevel.setModel(new SkillLevelComboBoxModel());
		this.comboBoxSecondSkillLevel.setEnabled(true);
		this.comboBoxFirstSkill.setModel(new HeroTraitComboBoxModel(isHotA, false, false));
		this.comboBoxFirstSkill.setEnabled(true);
		this.comboBoxSecondSkill.setModel(new HeroTraitComboBoxModel(isHotA, true, false));
		this.comboBoxSecondSkill.setEnabled(true);
		this.comboBoxSecondSkill.addItemListener(new HeroTraitComboBoxListener(this));
		this.comboBoxSpell.setModel(new SpellComboBoxModel());
		this.comboBoxSpell.setEnabled(true);
		this.comboBoxFirstTroop.setModel(new CreatureComboBoxModel(isHotA, false, totalCreatures));
		this.comboBoxFirstTroop.setEnabled(true);
		this.comboBoxSecondTroop.setModel(new CreatureComboBoxModel(isHotA, true, totalCreatures));
		this.comboBoxSecondTroop.setEnabled(true);
		this.comboBoxThirdTroop.setModel(new CreatureComboBoxModel(isHotA, false, totalCreatures));
		this.comboBoxThirdTroop.setEnabled(true);
		this.specialtyCreature.setModel(new CreatureSpecialtyComboBoxModel(isHotA, totalCreatures));
		this.specialtyCreatureStatic.setModel(new CreatureSpecialtyComboBoxModel(isHotA, totalCreatures));
		this.specialtyConversionResult.setModel(new CreatureComboBoxModel(isHotA, false, totalCreatures));
		this.specialtyFirstConversion.setModel(new CreatureComboBoxModel(isHotA, false, totalCreatures));
		this.specialtySecondConversion.setModel(new CreatureComboBoxModel(isHotA, false, totalCreatures));
		this.specialtySpell.setModel(new SpellSpecialtyComboBoxModel());
		this.specialtyResources.setModel(new ResourceComboBoxModel());
		this.specialtySkill.setModel(new HeroTraitComboBoxModel(isHotA, false, true));
		this.comboBoxSpecialty.setModel(new SpecialtyComboBoxModel(isHotA, totalHeroes));
		this.comboBoxSpecialty.addItemListener(new SpecialtyComboBoxListener(this));
		this.comboBoxSpecialty.setEnabled(true);
		this.comboBoxHero.setModel(new HeroComboBoxModel(heroes, totalHeroes));
		this.comboBoxHero.addItemListener(new HeroComboBoxItemListener(this));
		this.comboBoxHero.setEnabled(true);
		
		if (!heroes.isEmpty()) {
			this.comboBoxHero.setSelectedItem(heroes.get(0));
		} else {
			logger.warn("The list of heroes is empty!");
		}
		this.tableChanges.setModel(new ChangesTableModel(this, heroes));
		logger.info("Finished setting up components.");
	}
}
