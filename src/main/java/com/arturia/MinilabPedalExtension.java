package com.arturia;

import com.bitwig.extension.controller.api.*;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback;
import com.bitwig.extension.controller.api.Transport;
import com.bitwig.extension.controller.ControllerExtension;

public class MinilabPedalExtension extends ControllerExtension
{
   private static final int PEDAL_CC = 64;
   private static final int NUM_SCENES = 16;

   public static MidiOut mMidiOut;
   public static MidiIn mMidiIn;
   public static ControllerHost mHost;
   public static Clip mCursorClip;
   // public static ClipLauncherSlot mClipSlot;
   public static CursorTrack mCursorTrack;

   protected MinilabPedalExtension(final MinilabPedalExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }

   @Override
   public void init()
   {
      mHost = getHost();      

      mMidiIn = mHost.getMidiInPort(0);
      mMidiOut = mHost.getMidiOutPort(0);

      mMidiIn.setMidiCallback((ShortMidiMessageReceivedCallback) msg -> onMidi(msg));
      mMidiIn.createNoteInput("Minilab", "9?????", "8?????");

      mCursorClip = mHost.createLauncherCursorClip(1, 1);
      mCursorTrack = mHost.createCursorTrack(0, NUM_SCENES);
      mCursorTrack.isStopped().markInterested();
      mCursorClip.clipLauncherSlot().isRecording().markInterested();

      mHost.showPopupNotification("MinilabPedal Initialized");
   }

   @Override
   public void exit()
   {
      getHost().showPopupNotification("MinilabPedal Exited");
   }

   @Override
   public void flush()
   {
      // TODO Send any updates you need here.
   }

   private void processCC(int cc, int value) {
      // value == 0 means the button was released, not pressed
      if (value == 0) {
        return;
      }
      switch (cc) {
        case PEDAL_CC:
            if (mCursorClip.clipLauncherSlot().isRecording().get())
               mCursorClip.launch();
            else 
               mCursorTrack.recordNewLauncherClip(0);
            return;
      }
   }

   private void onMidi(ShortMidiMessage msg) {
      final int code = msg.getStatusByte() & 0xF0;
  
      switch (code) {
         // Note on/off
         case 0x80:
         case 0x90:
            break;
  
         // CC
         case 0xB0:
            processCC(msg.getData1(), msg.getData2());
            break;
  
         default:
            mHost.println("Unhandled midi status: " + msg.getStatusByte());
            break;
      }
    }
}
