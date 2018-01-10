package org.tc.osgi.bundle.apt.gui.ihm;

import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;

/**
 * ApplicationTest.java.
 * @req STD_BUNDLE_LISTECD_010
 * @track SRS_BUNDLE_LISTECD_010
 * @author Collonville Thomas
 * @version 0.7.0
 */
public class ApplicationTest extends FestSwingJUnitTestCase {

    /**
     * FrameFixture editor.
     */
    private FrameFixture editor;

    /**
     *
     * @see org.fest.swing.junit.testcase.FestSwingJUnitTestCase#onSetUp()
     */
    @Override
    protected void onSetUp() {
        //
        // editor = new FrameFixture(robot(), createNewEditor());
        // editor.show();
    }

    /**
     * createNewEditor.
     * @return MainFrame
     */
    // @RunsInEDT
    // private static AptGuiFrame createNewEditor() {
    // GuiQuery<AptGuiFrame> query = new GuiQuery<AptGuiFrame>() {
    // @Override
    // protected AptGuiFrame executeInEDT() {
    // AptGuiFrame app = null;
    // try {
    // app = new AptGuiFrame();
    // } catch (FieldTrackingAssignementException e) {
    // Assert.fail(e.getLocalizedMessage());
    // }
    // app.setVisible(true);
    // return app;
    // }
    // };
    // return GuiActionRunner.execute(query);
    //
    // }

    /**
     * profilCreationSelection.
     */
    // @Test
    // public void TestFrame() {
    // editor.close();
    // }

    // @Test
    // public void profilCreationSelection() {
    // editor.menuItemWithPath("Fichier", "Changer d'utilisateur").click();
    // editor.dialog("Login").button("create").click();
    // editor.dialog("Create a Login").textBox("profilName").requireText("John Doe");
    // editor.dialog("Create a Login").textBox("profilName").setText("tom");
    // editor.dialog("Create a Login").textBox("profilName").pressAndReleaseKeys(Event.ENTER);
    // editor.dialog("Create a Login").textBox("profilDir").requireText("target/tom/data/");
    // editor.dialog("Create a Login").button("Ok").requireEnabled();
    // editor.dialog("Create a Login").button("Ok").click();
    // editor.dialog("Login").comboBox().selectItem("tom").click();
    // editor.dialog("Login").button("select").click();
    //
    // editor.menuItemWithPath("Action", "Scanner").click();
    // editor.fileChooser().fileNameTextBox().setText("/home/thomas/Works/m2Repo");
    // editor.fileChooser().cancel();
    //
    // editor.menuItemWithPath("Action", "Chercher").click();
    // editor.dialog("NouvelleRecherche").textBox("chaineToFind").setText("org.tc");
    // editor.dialog("NouvelleRecherche").button("Cancel").click();
    //
    // editor.menuItemWithPath("Help", "About").click();
    // editor.dialog().button("Exit").click();
    //
    // editor.menuItemWithPath("Help", "Help").click();
    // editor.dialog().close();
    //
    // }

}
