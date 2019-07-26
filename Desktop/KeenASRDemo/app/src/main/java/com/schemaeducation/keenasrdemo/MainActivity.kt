package com.schemaeducation.keenasrdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.keenresearch.keenasr.KASRDecodingGraph;
import com.keenresearch.keenasr.KASRRecognizer;
import com.keenresearch.keenasr.KASRResult;
import com.keenresearch.keenasr.KASRRecognizerListener;
// if trigger phrase functionality is used
//import com.keenresearch.keenasr.KASRRecognizerTriggerPhraseListener;
import com.keenresearch.keenasr.KASRBundle;
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object MainActivityCompanion : KASRRecognizerListener{
        override fun onFinalResult(recognizer: KASRRecognizer?, result: KASRResult?) {
            Log.i("TAG: onFinalResult", "Final result: $result")
            Log.i("TAG: onFinalResult", "audioFile is in: $ " + recognizer?.lastRecordingFilename);
        }
        override fun onPartialResult(recognizer: KASRRecognizer?, result: KASRResult?) {
            Log.i("Tag: onPartialResult", "   Partial result: " + result?.text)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // KASRBundle:An instance of the KASRBundle class, called asrBundle,
        // manages ASR Bundle resources on the device file system
        val asrBundle = KASRBundle(this)
        val assets = ArrayList<String>()
        assets.add("keenB2mQT-nnet3chain-en-us/decode.conf")
        assets.add("keenB2mQT-nnet3chain-en-us/final.dubm")
        assets.add("keenB2mQT-nnet3chain-en-us/final.ie")
        assets.add("keenB2mQT-nnet3chain-en-us/final.mat")
        assets.add("keenB2mQT-nnet3chain-en-us/final.mdl")
        assets.add("keenB2mQT-nnet3chain-en-us/global_cmvn.stats")
        assets.add("keenB2mQT-nnet3chain-en-us/ivector_extractor.conf")
        assets.add("keenB2mQT-nnet3chain-en-us/mfcc.conf")
        assets.add("keenB2mQT-nnet3chain-en-us/online_cmvn.conf")
        assets.add("keenB2mQT-nnet3chain-en-us/splice.conf")
        assets.add("keenB2mQT-nnet3chain-en-us/splice_opts")
        assets.add("keenB2mQT-nnet3chain-en-us/wordBoundaries.int")
        assets.add("keenB2mQT-nnet3chain-en-us/words.txt")

        assets.add("keenB2mQT-nnet3chain-en-us/lang/lexicon.txt")
        assets.add("keenB2mQT-nnet3chain-en-us/lang/phones.txt")
        assets.add("keenB2mQT-nnet3chain-en-us/lang/tree")

        val asrBundleRootPath = applicationInfo.dataDir
        val asrBundlePath = asrBundleRootPath.plus("/keenB2mQT-nnet3chain-en-us")
        try{
            asrBundle.installASRBundle(assets, asrBundleRootPath)

        }catch (e: Exception){
            Log.e("TAG", "Error occurred when installing ASR bundle".plus(e))
        }


        // We now need to initialize the SDK using the path to the ASRBundle
        // this may take a long time, so we do it in a background thread.


        // KASRRecognizer: An instance of the KASRRecognizer class, called recognizer, manages
        // recognizer resources and provides speech recognition capabilities to your application.


        KASRRecognizer.initWithASRBundleAtPath(asrBundlePath, getApplicationContext())

        // You typically initialize the engine at the app startup time by calling
        // initWithASRBundleAtPath(String, Context) method, and then use sharedInstance() static
        // method when you need to access the recognizer.

        // Recognition results are provided via callbacks. To obtain results one of your
        // classes will need to adopt a KASRRecognizerListener interface and implement
        // some of its methods.

        val recognizer = KASRRecognizer.sharedInstance()

        // the addListener function is required to take in a KASRRecognizerListener interface.
        // What does it mean when a function takes in an interface as a parameter?

        // whatever we pass in as a parameter would need to implement the Listner.
        // so we have created a companion object, which can be used when needed and it implemented the required
        // interface

        recognizer.addListener(MainActivityCompanion)
        recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutEndSilenceForGoodMatch, 1.0f)
        recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutEndSilenceForAnyMatch, 1.0f)
        recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutMaxDuration, 10.0f)
        recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutForNoSpeech, 3.0f)
        recognizer.createAudioRecordings = true

        // Let's assume that the words in our sample list are just 3 simple words

        val phrases = arrayOf<String>("today","tomorrow","yesterday")
        if (recognizer!=null){
            val dgName: String = "words"
            KASRDecodingGraph.createDecodingGraphFromSentences(phrases, recognizer, dgName)
            recognizer.prepareForListeningWithCustomDecodingGraphWithName("words")
        }else {
            Log.e("TAG: decoding graph", "Unable to retrieve recognizer")
        }

        start_listening_button.setOnClickListener {
            Log.i("TAG:button listener", "starting to listen")
            recognizer.startListening()
        }


    }

    // Since you don't really have to access that right on launching, I can put the code for the button
    // outside the onCreate method.




}
