/**
 * @author Aubry Antoine
 * @author Faria dos Santos Dani Tiago
 */

package calculator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * @class JCalculator
 * @brief Classe qui implémente l'interface graphique d'une calculatrice.
 *
 * La classe JCalculator permet de créer une interface utilisateur pour une calculatrice
 * avec diverses opérations arithmétiques et gestion de la pile.
 */
public class JCalculator extends JFrame
{
  /**
   * @brief Tableau représentant une pile vide.
   */
  private static final String[] empty = { "< empty stack >" };

  /**
   * @brief Zone de texte contenant la valeur introduite ou le résultat courant.
   */
  private final JTextField jNumber = new JTextField("0");

  /**
   * @brief Composant liste représentant le contenu de la pile.
   */
  private final JList jStack = new JList(empty);

  /**
   * @brief Contraintes pour le placement des composants graphiques.
   */
  private final GridBagConstraints constraints = new GridBagConstraints();

  /**
   * @brief Met à jour l'interface graphique après une opération.
   *
   * Cette méthode met à jour la valeur dans la zone de texte jNumber et le contenu de la pile affichée dans jStack.
   */
  private void update()
  {
    jNumber.setText(State.getState().getValueString());

    Object[] stackArray = State.getState().stackToArray(); // Récupère les éléments de la pile
    jStack.setListData(stackArray); // Met à jour la JList avec les éléments actuels de la pile

    if(State.getState().isStackEmpty())
      jStack.setListData(empty);
  }

  /**
   * @brief Ajoute un bouton à l'interface et associe une opération.
   *
   * @param name Nom du bouton.
   * @param x Position horizontale dans la grille.
   * @param y Position verticale dans la grille.
   * @param color Couleur du texte du bouton.
   * @param operator Instance de l'opérateur associé au bouton.
   *
   * Cette méthode crée un bouton avec un opérateur donné. Lorsqu'on clique sur le bouton, l'opérateur est exécuté et l'interface est mise à jour.
   */
  private void addOperatorButton(String name, int x, int y, Color color,
                                 final Operator operator)
  {
    JButton b = new JButton(name);
    b.setForeground(color);
    constraints.gridx = x;
    constraints.gridy = y;
    getContentPane().add(b, constraints);
    b.addActionListener(e -> {
      operator.execute();
      update();
    });
  }

  /**
   * @brief Constructeur de la classe JCalculator.
   *
   * Ce constructeur initialise l'interface utilisateur de la calculatrice en configurant
   * la disposition et en ajoutant les différents boutons nécessaires.
   */
  public JCalculator()
  {
    super("JCalculator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(new GridBagLayout());

    // Contraintes des composants graphiques
    constraints.insets = new Insets(3, 3, 3, 3);
    constraints.fill = GridBagConstraints.HORIZONTAL;

    // Nombre courant
    jNumber.setEditable(false);
    jNumber.setBackground(Color.WHITE);
    jNumber.setHorizontalAlignment(JTextField.RIGHT);
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.gridwidth = 5;
    getContentPane().add(jNumber, constraints);
    constraints.gridwidth = 1; // reset width

    // Rappel de la valeur en memoire
    addOperatorButton("MR", 0, 1, Color.RED, new MemoryRecall());

    // Stockage d'une valeur en memoire
    addOperatorButton("MS", 1, 1, Color.RED, new MemoryStore());

    // Backspace
    addOperatorButton("<=", 2, 1, Color.RED, new BackSpace());

    // Mise a zero de la valeur courante + suppression des erreurs
    addOperatorButton("CE", 3, 1, Color.RED, new ClearError());

    // Comme CE + vide la pile
    addOperatorButton("C",  4, 1, Color.RED, new Clear());

    // Boutons 1-9
    for (int i = 1; i < 10; i++)
      addOperatorButton(String.valueOf(i), (i - 1) % 3, 4 - (i - 1) / 3,
              Color.BLUE, new Digit(i));
    // Bouton 0
    addOperatorButton("0", 0, 5, Color.BLUE, new Digit(0));

    // Changement de signe de la valeur courante
    addOperatorButton("+/-", 1, 5, Color.BLUE, new ChangeSign());

    // Operateur point (chiffres apres la virgule ensuite)
    addOperatorButton(".", 2, 5, Color.BLUE, new AppendDot());

    // Operateurs arithmetiques a deux operandes: /, *, -, +
    addOperatorButton("/", 3, 2, Color.RED, new Division());
    addOperatorButton("*", 3, 3, Color.RED, new Multiplication());
    addOperatorButton("-", 3, 4, Color.RED, new Subtraction());
    addOperatorButton("+", 3, 5, Color.RED, new Addition());

    // Operateurs arithmetiques a un operande: 1/x, x^2, Sqrt
    addOperatorButton("1/x", 4, 2, Color.RED, new Reciprocal());
    addOperatorButton("x²", 4, 3, Color.RED, new Square());
    addOperatorButton("Sqrt", 4, 4, Color.RED, new SquareRoot());

    // Entree: met la valeur courante sur le sommet de la pile
    addOperatorButton("Ent", 4, 5, Color.RED, new Enter());

    // Affichage de la pile
    JLabel jLabel = new JLabel("Stack");
    jLabel.setFont(new Font("Dialog", 0, 12));
    jLabel.setHorizontalAlignment(JLabel.CENTER);
    constraints.gridx = 5;
    constraints.gridy = 0;
    getContentPane().add(jLabel, constraints);

    jStack.setFont(new Font("Dialog", 0, 12));
    jStack.setVisibleRowCount(8);
    JScrollPane scrollPane = new JScrollPane(jStack);
    constraints.gridx = 5;
    constraints.gridy = 1;
    constraints.gridheight = 5;
    getContentPane().add(scrollPane, constraints);
    constraints.gridheight = 1; // reset height

    setResizable(false);
    pack();
    setVisible(true);
  }
}
