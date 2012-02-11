package structure

/*
   This example is an improvement over "AllInOne" in that it put the various aspects of UI generation
   in different places.
   Style information has mainly gone into DemoStyle and demo.css. I wish the alignment tweaks could also go there.
   The approach may be further extended into a proper lifecycle management, the use of presentation models,
   controllers, and full-blown meta-design for reusable layouts.
   @author Dierk Koenig
*/

import static groovyx.javafx.GroovyFX.start
import groovyx.javafx.beans.FXBindable
import groovyx.javafx.SceneGraphBuilder
import static javafx.geometry.HPos.*
import static javafx.geometry.VPos.*
import javafx.event.EventHandler

class Email2 {
    @FXBindable String name, address, feedback
    String toString() { "<$name> $address : $feedback" }
}

start { app ->
    SceneGraphBuilder builder = delegate
    layoutFrame       builder
    DemoStyle.style   builder

    def model       = new Email2()
    bindModelToViews  model, builder
    attachHandlers    model, builder

    primaryStage.show()
}

def layoutFrame(SceneGraphBuilder sgb) {
    sgb.stage {
        scene {
            gridPane {
                label       id:'header',    row: 0, column: 1,
                            'Please Send Us Your Feedback'

                label       'Name',         row: 1, column: 0
                textField   id: 'name',     row: 1, column: 1

                label       'Address',      row: 2, column: 0
                textField   id: 'address',  row: 2, column: 1

                label       'Feedback',     row: 3, column: 0, valignment: BASELINE
                textArea    id: 'feedback', row: 3, column: 1

                button      id: 'submit',   row: 4, column: 1, halignment: RIGHT,
                            "Send Feedback"
}   }   }   }


void bindModelToViews(Email2 email, SceneGraphBuilder sgb) {
    sgb.with {
        email.nameProperty()    .bind name.textProperty()
        email.addressProperty() .bind address.textProperty()
        email.feedbackProperty().bind feedback.textProperty()
    }
}

void attachHandlers(Email2 email, SceneGraphBuilder sgb) {
    sgb.submit.onAction = { println "preparing and sending the mail: $email" } as EventHandler
}
