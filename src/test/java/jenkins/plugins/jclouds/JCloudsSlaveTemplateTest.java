package jenkins.plugins.jclouds;

import org.jvnet.hudson.test.HudsonTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vijay Kiran
 */
public class JCloudsSlaveTemplateTest extends HudsonTestCase {

   public void testConfigRoundtrip() throws Exception {
      String name = "testSlave";
      JCloudsSlaveTemplate originalTemplate = new JCloudsSlaveTemplate(name, 1, 512, "osFamily", "osVersion", "jclouds-slave-type1 jclouds-type2", "BLAH BLAH");
      List<JCloudsSlaveTemplate> templates = new ArrayList<JCloudsSlaveTemplate>();
      templates.add(originalTemplate);

      JCloudsCloud originalCloud = new JCloudsCloud("aws-profile", "aws-ec2", "identity", "credential", "privateKey", "publicKey",
            "endPointUrl", templates);

      hudson.clouds.add(originalCloud);
      submit(createWebClient().goTo("configure").getFormByName("config"));

      assertEqualBeans(originalCloud,
            hudson.clouds.iterator().next(),
            "profile,providerName,identity,credential,privateKey,publicKey,endPointUrl");

      assertEqualBeans(originalTemplate,
            ((JCloudsCloud)hudson.clouds.iterator().next()).getTemplate(name),
            "name");
   }

}
