package com.arturia;
import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class MinilabPedalExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("3dc6f984-e7ab-4260-ac0f-46418d098a2f");

   public MinilabPedalExtensionDefinition()
   {
   }

   @Override
   public String getName()
   {
      return "MinilabPedal";
   }
   
   @Override
   public String getAuthor()
   {
      return "Netsu";
   }

   @Override
   public String getVersion()
   {
      return "0.1";
   }

   @Override
   public UUID getId()
   {
      return DRIVER_ID;
   }
   
   @Override
   public String getHardwareVendor()
   {
      return "Arturia";
   }
   
   @Override
   public String getHardwareModel()
   {
      return "MinilabPedal";
   }

   @Override
   public int getRequiredAPIVersion()
   {
      return 17;
   }

   @Override
   public int getNumMidiInPorts()
   {
      return 1;
   }

   @Override
   public int getNumMidiOutPorts()
   {
      return 1;
   }

   @Override
   public void listAutoDetectionMidiPortNames(final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
   {
   }

   @Override
   public MinilabPedalExtension createInstance(final ControllerHost host)
   {
      return new MinilabPedalExtension(this, host);
   }
}
