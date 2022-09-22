import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

class Symptom extends SymptomBase {

	public Symptom(String symptom, int severity) {
		super(symptom, severity);
	}

	@Override
	public int compareTo(SymptomBase o) {
		if (this.getSeverity() > o.getSeverity()) {
			return 1;
		} else if (this.getSeverity() < o.getSeverity()) {
			return  -1;
		} else {
			return 0;
		}
	}
}

public class TreeOfSymptoms extends TreeOfSymptomsBase {

	public TreeOfSymptoms(SymptomBase root) {
		super(root);
	}

	@Override
	public ArrayList<SymptomBase> inOrderTraversal() {
		ArrayList<SymptomBase> symptoms = new ArrayList<>();
		getInOrder((Symptom) this.getRoot(), symptoms);
		return symptoms;
	}

	@Override
	public ArrayList<SymptomBase> postOrderTraversal() {
		ArrayList<SymptomBase> symptoms = new ArrayList<>();
		getPostOrder((Symptom) this.getRoot(), symptoms);
		return symptoms;
	}

	@Override
	public void restructureTree(int severity) {
		// Get a list of all nodes in the tree
		ArrayList<SymptomBase> symptoms = inOrderTraversal();
		Collections.sort(symptoms);
		int i = 0;
		SymptomBase newRoot = symptoms.get(0);

		// Find the new root node which is equal or greater to a given severity threshold,
		// or take the maximum if threshold cannot be met
		while (newRoot.getSeverity() < severity) {
			i++;
			if (i < symptoms.size()){
				newRoot = symptoms.get(i);
			} else {
				newRoot = Collections.max(symptoms);
				break;
			}
		}

		// Reset left and right pointers for each node
		for (SymptomBase symptom : symptoms) {
			symptom.setLeft(null);
			symptom.setRight(null);
		}

		// Reset root of the tree and remove it from list of symptoms
		this.setRoot(newRoot);
		symptoms.remove(newRoot);

		for (SymptomBase newSymptom : symptoms) {
			this.insertSymptom(newRoot, newSymptom);
		}

	}

	/**
	 * Insert symptom nodes into the binary search tree.
	 * @param root Root or parent node to insert under.
	 * @param newSymptom New symptom node to be inserted into tree.
	 * @return Node to insert.
	 */
	private SymptomBase insertSymptom(SymptomBase root, SymptomBase newSymptom) {
		if (root == null) {
			return newSymptom;
		} else if (newSymptom.getSeverity() > root.getSeverity()) {
			root.setRight(insertSymptom(root.getRight(), newSymptom));
		} else if (newSymptom.getSeverity() < root.getSeverity()) {
			root.setLeft(insertSymptom(root.getLeft(), newSymptom));
		}
		return root;
	}

	/**
	 * Adds symptoms into a list based on the in-order traversal.
	 * @param symptom Symptom node to explore.
	 * @param list List where symptoms should be added to.
	 */
	private void getInOrder(Symptom symptom, ArrayList<SymptomBase> list) {
		if (symptom == null) {
			return;
		}

		getInOrder((Symptom) symptom.getLeft(), list);
		list.add(symptom);
		getInOrder((Symptom) symptom.getRight(), list);
	}

	/**
	 * Adds symptoms into a list based on the post-order traversal.
	 * @param symptom Symptom node to explore.
	 * @param list List where symptoms should be added to.
	 */
	private void getPostOrder(Symptom symptom, ArrayList<SymptomBase> list) {
		if (symptom == null) {
			return;
		}

		getPostOrder((Symptom) symptom.getLeft(), list);
		getPostOrder((Symptom) symptom.getRight(), list);
		list.add(symptom);
	}

	public static void main(String[] args) {
		/*
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 * REMOVE THE MAIN METHOD BEFORE SUBMITTING TO THE AUTOGRADER
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 * The following main method is provided for simple debugging only
		 */
		var cough = new Symptom("Cough", 3);
		var fever = new Symptom("Fever", 6);
		var redEyes = new Symptom("Red Eyes", 1);

		redEyes.setLeft(cough);
		redEyes.setRight(fever);

		var tree = new TreeOfSymptoms(redEyes);
		var inOrderTraversal = tree.inOrderTraversal();
		var correctTraversal = new Symptom[] { cough, redEyes, fever };
		int i = 0;
		for (var patient : inOrderTraversal) {
			assert Objects.equals(patient, correctTraversal[i++]);
		}
		assert tree.getRoot() == redEyes;

		tree.restructureTree(2);
		inOrderTraversal = tree.inOrderTraversal();
		correctTraversal = new Symptom[] { redEyes, cough, fever};
		i = 0;
		for (var patient : inOrderTraversal) {
			assert Objects.equals(patient, correctTraversal[i++]);
		}
		assert tree.getRoot() == cough;
	}
}
