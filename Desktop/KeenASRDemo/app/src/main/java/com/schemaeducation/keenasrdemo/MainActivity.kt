package com.schemaeducation.keenasrdemo

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.keenresearch.keenasr.KASRDecodingGraph;
import com.keenresearch.keenasr.KASRRecognizer;
import com.keenresearch.keenasr.KASRResult;
import com.keenresearch.keenasr.KASRRecognizerListener;
import com.keenresearch.keenasr.KASRBundle;
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), KASRRecognizerListener {


    private val asyncASRInitializerTask: ASyncASRInitializerTask?=null
    val instance : MainActivity?=null


    override fun onFinalResult(recognizer: KASRRecognizer?, result: KASRResult?) {
        Log.i("TAG: onFinalResult", "Final result: $result")
        Log.i("TAG: onFinalResult", "audioFile is in: $ " + recognizer?.lastRecordingFilename);
    }
    override fun onPartialResult(recognizer: KASRRecognizer?, result: KASRResult?) {
        Log.i("Tag: onPartialResult", "   Partial result: " + result?.text)
    }

/*
    companion object MainActivityCompanion : KASRRecognizerListener{
        override fun onFinalResult(recognizer: KASRRecognizer?, result: KASRResult?) {
            Log.i("TAG: onFinalResult", "Final result: $result")
            Log.i("TAG: onFinalResult", "audioFile is in: $ " + recognizer?.lastRecordingFilename);
        }
        override fun onPartialResult(: KASRRecognizer?, result: KASRResult?) {
            Log.i("Tag: onPartialResult", "   Partial result: " + result?.text)
        }
    }
*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (KASRRecognizer.sharedInstance() == null){
            Log.i("TAG_onCreate", "Initializing KeenASR recognizer")
            KASRRecognizer.setLogLevel(KASRRecognizer.KASRRecognizerLogLevel.KASRRecognizerLogLevelDebug)
            val context : Context = this@MainActivity
            val asyncASRInitializerTask = ASyncASRInitializerTask(context)
            asyncASRInitializerTask.execute()
        }

        val recognizer = KASRRecognizer.sharedInstance()

        start_listening_button.setOnClickListener {
            Log.i("TAG:button listener", "starting to listen")
            recognizer.startListening()
        }



/*        // KASRBundle:An instance of the KASRBundle class, called asrBundle,
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
        println("DATADIR$asrBundlePath")
        try{
            asrBundle.installASRBundle(assets, asrBundleRootPath)

        }catch (e: Exception){
            Log.e("TAG", "Error occurred when installing ASR bundle".plus(e))
        }


        // We now need to initialize the SDK using the path to the ASRBundle
        // this may take a long time, so we do it in a background thread.


        // KASRRecognizer: An instance of the KASRRecognizer class, called recognizer, manages
        // recognizer resources and provides speech recognition capabilities to your application.

        KASRRecognizer.initWithASRBundleAtPath(asrBundlePath, applicationContext)
        // The API had explicitly mentioned that this will take some time, and recommends that we do it in the
        // background. If I do not do it in the background....it should still not crash the app.

        val recognizer = KASRRecognizer.sharedInstance()

        // You typically initialize the engine at the app startup time by calling
        // initWithASRBundleAtPath(String, Context) method, and then use sharedInstance() static
        // method when you need to access the recognizer.

        // Recognition results are provided via callbacks. To obtain results one of your
        // classes will need to adopt a KASRRecognizerListener interface and implement
        // some of its methods.

        if (recognizer == null){
            println("KASRRecognizer IS NULL")
        }

        // the addListener function is required to take in a KASRRecognizerListener interface.
        // What does it mean when a function takes in an interface as a parameter?

        // whatever we pass in as a parameter would need to implement the Listener.
        // so we have created a companion object, which can be used when needed and it implemented the required
        // interface

        if (recognizer!=null){
            recognizer.addListener(MainActivityCompanion)
            recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutEndSilenceForGoodMatch, 1.0f)
            recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutEndSilenceForAnyMatch, 1.0f)
            recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutMaxDuration, 10.0f)
            recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutForNoSpeech, 3.0f)
            recognizer.createAudioRecordings = true
        }

        // Let's assume that the words in our sample list are just 3 simple words

        val phrases = arrayOf<String>("today","tomorrow","yesterday")
        if (recognizer!=null){
            val dgName: String = "words"
            KASRDecodingGraph.createDecodingGraphFromSentences(phrases, recognizer, dgName)
            recognizer.prepareForListeningWithCustomDecodingGraphWithName("words")
        }else {
            Log.e("TAG: decoding graph", "Unable to retrieve recognizer")
        }*/

/*
        start_listening_button.setOnClickListener {
            Log.i("TAG:button listener", "starting to listen")
            recognizer.startListening()
        }
*/

    }

    private class ASyncASRInitializerTask constructor(val context: Context): AsyncTask<String, Integer, Long>() {
        override fun doInBackground(vararg params: String?): Long {
            Log.i("ASYNC_TAG","Installing ASR Bundle" )

            val asrBundle = KASRBundle(context)
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

            val asrBundleRootPath = context.applicationInfo.dataDir
            val asrBundlePath = asrBundleRootPath.plus("/keenB2mQT-nnet3chain-en-us")
            println("DATADIR$asrBundlePath")
            try{
                asrBundle.installASRBundle(assets, asrBundleRootPath)
                Log.i("TAG", "installed ASR bundle")

            }catch (e: Exception){
                Log.e("TAG", "Error occurred when installing ASR bundle".plus(e))
            }


            // We now need to initialize the SDK using the path to the ASRBundle
            // this may take a long time, so we do it in a background thread.


            // KASRRecognizer: An instance of the KASRRecognizer class, called recognizer, manages
            // recognizer resources and provides speech recognition capabilities to your application.

            KASRRecognizer.initWithASRBundleAtPath(asrBundlePath, context)

            val phrases = arrayOf<String>("today","tomorrow","yesterday")
            val recognizer = KASRRecognizer.sharedInstance()

            if (recognizer != null){

                val dgName:String  = "words"
                if (KASRDecodingGraph.decodingGraphWithNameExists(dgName, recognizer)){
                    Log.i("TAG_DECODING", "Decoding graphp ".plus(dgName).plus(" already exists. IT WONT BE RECREATED"))
                    Log.i("TAG_DECODIGN", "Created on".plus(KASRDecodingGraph.getDecodingGraphCreationDate(dgName,recognizer)))
                }else{
                    KASRDecodingGraph.createDecodingGraphFromSentences(phrases,recognizer,dgName)
                }
                recognizer.prepareForListeningWithCustomDecodingGraphWithName(dgName)
            }else{
                Log.e("TAG_DECODING", "Unable to retrieve recognizer")
            }
            return 11
        }

        override fun onProgressUpdate(vararg values: Integer?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
            Log.i("TAG_OnPostExecute", "Initialized KeenASR in the BackGround")
            val recognizer = KASRRecognizer.sharedInstance()
            if (recognizer!=null){
                Log.i("TAG_onPostExecute", "Adding Listener")
                recognizer.addListener(this@MainActivity)
                recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutEndSilenceForGoodMatch, 1.0f)
                recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutEndSilenceForAnyMatch, 1.0f)
                recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutMaxDuration, 15.0f)
                recognizer.setVADParameter(KASRRecognizer.KASRVadParameter.KASRVadTimeoutForNoSpeech, 5.0f)
                recognizer.createAudioRecordings = true
                Log.i("TAG_onPostExecute", "reached createAudioRecordings")
            }else{
                Log.e("TAG_onPostExecute", "Recognizer wasn't initialized properly")
            }

        }

    }

}
