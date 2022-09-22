import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class TreeOfSymptomsTest {
    private TreeOfSymptoms tree;

    @Test
    public void testOneElementTraversal() {
        Symptom cough = new Symptom("Cough", 3);
        tree = new TreeOfSymptoms(cough);

        // Check traversals are correct
        ArrayList<SymptomBase> inOrder = tree.inOrderTraversal();
        ArrayList<SymptomBase> correctInOrder = new ArrayList<>();
        correctInOrder.add(cough);
        assertEquals(correctInOrder, inOrder);

        ArrayList<SymptomBase> postOrder = tree.postOrderTraversal();
        ArrayList<SymptomBase> correctPostOrder = new ArrayList<>();
        correctPostOrder.add(cough);
        assertEquals(correctPostOrder, postOrder);
    }

    @Test
    public void testTwoElementTraversal() {
        Symptom redEyes = new Symptom("Red Eyes", 1);
        Symptom cough = new Symptom("Cough", 3);
        Symptom headache = new Symptom("Headache", 4);
        Symptom fever = new Symptom("Fever", 6);
        cough.setLeft(fever);
        redEyes.setRight(headache);
        TreeOfSymptoms treeLeft = new TreeOfSymptoms(cough);
        TreeOfSymptoms treeRight = new TreeOfSymptoms(redEyes);

        ArrayList<SymptomBase> inOrder;
        ArrayList<SymptomBase> correctInOrder;
        ArrayList<SymptomBase> postOrder;
        ArrayList<SymptomBase> correctPostOrder;

        // Check left-only traversals are correct
        inOrder = treeLeft.inOrderTraversal();
        correctInOrder = new ArrayList<>();
        correctInOrder.add(fever);
        correctInOrder.add(cough);
        assertEquals(correctInOrder, inOrder);

        postOrder = treeLeft.postOrderTraversal();
        correctPostOrder = new ArrayList<>();
        correctPostOrder.add(fever);
        correctPostOrder.add(cough);
        assertEquals(correctPostOrder, postOrder);

        // Check right-only traversals are correct
        inOrder = treeRight.inOrderTraversal();
        correctInOrder = new ArrayList<>();
        correctInOrder.add(redEyes);
        correctInOrder.add(headache);
        assertEquals(correctInOrder, inOrder);

        postOrder = treeRight.postOrderTraversal();
        correctPostOrder = new ArrayList<>();
        correctPostOrder.add(headache);
        correctPostOrder.add(redEyes);
        assertEquals(correctPostOrder, postOrder);
    }

    @Test
    public void testMultipleElementTraversal() {
        Symptom redEyes = new Symptom("Red Eyes", 1);
        Symptom runnyNose = new Symptom("Runny Nose", 2);
        Symptom cough = new Symptom("Cough", 3);
        Symptom headache = new Symptom("Headache", 4);
        Symptom fatigue = new Symptom("Fatigue", 5);
        Symptom fever = new Symptom("Fever", 6);
        Symptom vomiting = new Symptom("Vomiting", 7);
        redEyes.setLeft(runnyNose);
        redEyes.setRight(cough);
        runnyNose.setRight(headache);
        headache.setLeft(fatigue);
        cough.setLeft(fever);
        cough.setRight(vomiting);
        tree = new TreeOfSymptoms(redEyes);

        // Check traversals are correct
        ArrayList<SymptomBase> inOrder = tree.inOrderTraversal();
        ArrayList<SymptomBase> correctInOrder = new ArrayList<>();
        correctInOrder.add(runnyNose);
        correctInOrder.add(fatigue);
        correctInOrder.add(headache);
        correctInOrder.add(redEyes);
        correctInOrder.add(fever);
        correctInOrder.add(cough);
        correctInOrder.add(vomiting);
        assertEquals(correctInOrder, inOrder);

        ArrayList<SymptomBase> postOrder = tree.postOrderTraversal();
        ArrayList<SymptomBase> correctPostOrder = new ArrayList<>();
        correctPostOrder.add(fatigue);
        correctPostOrder.add(headache);
        correctPostOrder.add(runnyNose);
        correctPostOrder.add(fever);
        correctPostOrder.add(vomiting);
        correctPostOrder.add(cough);
        correctPostOrder.add(redEyes);
        assertEquals(correctPostOrder, postOrder);
    }

    @Test
    public void testRestructureOneElement() {
        Symptom cough = new Symptom("Cough", 3);
        tree = new TreeOfSymptoms(cough);
        ArrayList<SymptomBase> inOrder;
        ArrayList<SymptomBase> correctInOrder;

        // Restructure based on a threshold lower than available
        tree.restructureTree(1);
        inOrder = tree.inOrderTraversal();
        correctInOrder = new ArrayList<>();
        correctInOrder.add(cough);
        assertEquals(correctInOrder, inOrder);

        // Restructure based on a threshold larger than available
        tree.restructureTree(5);
        inOrder = tree.inOrderTraversal();
        correctInOrder = new ArrayList<>();
        correctInOrder.add(cough);
        assertEquals(correctInOrder, inOrder);

        // Restructure based on a severity which exists
        tree.restructureTree(3);
        inOrder = tree.inOrderTraversal();
        correctInOrder = new ArrayList<>();
        correctInOrder.add(cough);
        assertEquals(correctInOrder, inOrder);
    }

    @Test
    public void testRestructureTwoElements() {
        Symptom redEyes = new Symptom("Red Eyes", 2);
        Symptom cough = new Symptom("Cough", 3);
        Symptom headache = new Symptom("Headache", 4);
        Symptom fever = new Symptom("Fever", 6);
        cough.setLeft(fever);
        headache.setRight(redEyes);
        TreeOfSymptoms treeLeft = new TreeOfSymptoms(cough);
        TreeOfSymptoms treeRight = new TreeOfSymptoms(headache);

        ArrayList<SymptomBase> postOrder;
        ArrayList<SymptomBase> correctPostOrder;

        // Restructure based on a threshold lower than available
        treeLeft.restructureTree(1);
        postOrder = treeLeft.postOrderTraversal();
        correctPostOrder = new ArrayList<>();
        correctPostOrder.add(fever);
        correctPostOrder.add(cough);
        assertEquals(correctPostOrder, postOrder);

        treeRight.restructureTree(1);
        postOrder = treeRight.postOrderTraversal();
        correctPostOrder = new ArrayList<>();
        correctPostOrder.add(headache);
        correctPostOrder.add(redEyes);
        assertEquals(correctPostOrder, postOrder);

        // Restructure based on a threshold larger than available
        treeLeft.restructureTree(10);
        postOrder = treeLeft.postOrderTraversal();
        correctPostOrder = new ArrayList<>();
        correctPostOrder.add(cough);
        correctPostOrder.add(fever);
        assertEquals(correctPostOrder, postOrder);

        treeRight.restructureTree(10);
        postOrder = treeRight.postOrderTraversal();
        correctPostOrder = new ArrayList<>();
        correctPostOrder.add(redEyes);
        correctPostOrder.add(headache);
        assertEquals(correctPostOrder, postOrder);

        // Restructure based on a severity which exists
        treeLeft.restructureTree(3);
        postOrder = treeLeft.postOrderTraversal();
        correctPostOrder = new ArrayList<>();
        correctPostOrder.add(fever);
        correctPostOrder.add(cough);
        assertEquals(correctPostOrder, postOrder);

        treeRight.restructureTree(2);
        postOrder = treeRight.postOrderTraversal();
        correctPostOrder = new ArrayList<>();
        correctPostOrder.add(headache);
        correctPostOrder.add(redEyes);
        assertEquals(correctPostOrder, postOrder);
    }
}
