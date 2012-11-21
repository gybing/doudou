package com.doudoumobile.etonkids_client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.androidpn.client.Constants;
import org.ebookdroid.core.ContiniousDocumentView;
import org.ebookdroid.core.DecodeService;
import org.ebookdroid.core.DecodeServiceBase;
import org.ebookdroid.core.IDocumentViewController;
import org.ebookdroid.core.IViewerActivity;
import org.ebookdroid.core.PageIndex;
import org.ebookdroid.core.SinglePageDocumentView;
import org.ebookdroid.core.events.CurrentPageListener;
import org.ebookdroid.core.events.DecodingProgressListener;
import org.ebookdroid.core.models.DecodingProgressModel;
import org.ebookdroid.core.models.DocumentModel;
import org.ebookdroid.core.models.ZoomModel;
import org.ebookdroid.core.multitouch.MultiTouchZoom;
import org.ebookdroid.core.settings.AppSettings;
import org.ebookdroid.core.settings.AppSettings.Diff;
import org.ebookdroid.core.settings.BookSettings;
import org.ebookdroid.core.settings.ISettingsChangeListener;
import org.ebookdroid.core.settings.SettingsManager;
import org.ebookdroid.pdfdroid.codec.PdfContext;
import org.miscwidgets.interpolator.BackInterpolator;
import org.miscwidgets.interpolator.EasingType.Type;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.doudoumobile.etonkids_client.Panel.OnPanelListener;
import com.doudoumobile.etonkids_client.model.Lesson;
import com.doudoumobile.etonkids_client.model.Material;

public class LessonActivity extends Activity implements OnPanelListener, IViewerActivity, DecodingProgressListener,
	CurrentPageListener, ISettingsChangeListener{

	private static String TAG = "LESSON_ACTIVITY";
	
	private LinearLayout picview;
	private LinearLayout lessonLayout;
	
	private String sdCard = Constants.SD_PATH;
	
	private DecodeService decodeService;
	private ZoomModel zoomModel;
	private IDocumentViewController documentController;
	private DecodingProgressModel progressModel;
	private Toast pageNumberToast;
	public static final DisplayMetrics DM = new DisplayMetrics();
    private MultiTouchZoom multiTouchZoom;

    private DocumentModel documentModel;
	ProgressDialog progressDialog;
	
	ImageButton backBtn;
	ImageButton materialListBtn;
	private Panel resourcePanel;
	private Button panelHandle;
	private MediaplayerSActivity smallMediaPlayer;
	
	private Lesson lesson;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        picview = (LinearLayout)findViewById(R.id.picview);
        lessonLayout = (LinearLayout)findViewById(R.id.lessonLayout);
        smallMediaPlayer = (MediaplayerSActivity)findViewById(R.id.mediaplayerSActivity1);
        
        Bundle b=getIntent().getExtras();
        lesson = (Lesson)b.getSerializable("lesson");
        SettingsManager.init(getContext());
        SettingsManager.addListener(this);
        SettingsManager.applyAppSettingsChanges(null, SettingsManager.getAppSettings());
        getWindowManager().getDefaultDisplay().getMetrics(DM);
        initButton();
        initPdfService();
        initMaterial();
    }
	
	private void initButton() {
		backBtn = (ImageButton)findViewById(R.id.imageButton1);
        materialListBtn = (ImageButton)findViewById(R.id.imageButton2);
        
        resourcePanel = (Panel)findViewById(R.id.bottomPanel);
        resourcePanel.setOnPanelListener(this);
        resourcePanel.setInterpolator(new BackInterpolator(Type.OUT, 2));
        
        backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LessonActivity.this, IndexActivity.class);
				recycleResource();
				startActivity(intent);
			}
		});
        
        materialListBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				smallMediaPlayer.stop();
				
				Intent intent = new Intent(LessonActivity.this, LibraryActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("lesson", lesson);
				intent.putExtras(bundle);
				recycleResource();
				startActivity(intent);
			}
		});
        
        panelHandle = (Button)findViewById(R.id.panelHandle);
	}
	
	private void initPdfService() {
		initDecodeService();
	}
	
	private void recycleResource() {
		//recycle
		System.out.println("Recycling");
		if (documentModel != null) {
            documentModel.recycle();
            documentModel = null;
        }
        if (null != picview){
        	picview.removeAllViews();
        	picview = null;
        }
        SettingsManager.removeListener(this);
        smallMediaPlayer.stop();
        System.gc();
	}
	private void startDecoding(final DecodeService decodeService, final String fileName, final String password) {
        new AsyncTask<String, Void, Exception>() {

            @Override
            protected void onPreExecute() {
                Log.d(TAG,"onPreExecute(): start");
                try {
                    progressDialog = ProgressDialog.show(LessonActivity.this, "", "Loading... Please wait", true);
                } catch (Throwable th) {
                	Log.d(TAG, th.getMessage());
                } finally {
                	Log.d(TAG,"onPreExecute(): finish");
                }
            }

            @Override
            protected Exception doInBackground(final String... params) {
                Log.d(TAG,"doInBackground(): start");
                System.out.println(params[0] + " " +  params[1]);
                try {
                    decodeService.open(params[0], params[1]);
                    return null;
                } catch (final Exception e) {
                	Log.e(TAG,e.getMessage());
                    return e;
                } catch (final Throwable th) {
                    Log.e(TAG,th.getMessage());
                    return new Exception(th.getMessage());
                } finally {
                    Log.d(TAG,"doInBackground(): finish");
                }
            }

            @Override
            protected void onPostExecute(final Exception result) {
                Log.d(TAG,"onPostExecute(): start");
                try {
                    if (result == null) {
                        zoomModel = new ZoomModel();
                        initMultiTouchZoomIfAvailable();
                        //setContentView(frameLayout);

                        documentModel = new DocumentModel(decodeService);
                        documentModel.addEventListener(LessonActivity.this);
                        progressModel = new DecodingProgressModel();
                        progressModel.addEventListener(LessonActivity.this);

                        SettingsManager.applyBookSettingsChanges(null, SettingsManager.getBookSettings(), null);
                        if (documentModel != null) {
                            final BookSettings bs = SettingsManager.getBookSettings();
                            if (bs != null) {
                                currentPageChanged(PageIndex.NULL, bs.getCurrentPage());
                            }
                        }
                        setProgressBarIndeterminateVisibility(false);

                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();

                        final String msg = result.getMessage();
                        if ("PDF needs a password!".equals(msg)) {
                            System.out.println("Need Password");
                        } else {
                           System.out.println("Error " + msg);
                        }
                    }
                } catch (final Throwable th) {
                    Log.e(TAG,th.getMessage());
                } finally {
                    Log.d(TAG,"onPostExecute(): finish");
                }
            }
        }.execute(fileName, password);
    }
	
	private void initMultiTouchZoomIfAvailable() {
        try {
            multiTouchZoom = ((MultiTouchZoom) Class.forName("org.ebookdroid.core.multitouch.MultiTouchZoomImpl")
                    .getConstructor(ZoomModel.class).newInstance(zoomModel));
        } catch (final Exception e) {
            System.out.println("Multi touch zoom is not available: " + e);
        }
    }
	
	private void initMaterial() {
		List<Material> resourceList = lesson.getMaterialList();
		ArrayList<Material> musicList = new ArrayList<Material>();
		for (Material material : resourceList) { 
			//picture
			if (material.getType() == 2) {
				insertPic(material);
			} else if (material.getType() == 0 || material.getType() == 1){ //mp3
				musicList.add(material);
				//initMediaPlayer(material);
			} else if (material.getType() == 3) {//pdf
				showPdf(material.getPath());
			}
		}
		initMediaPlayer(musicList);
	}
	
	private void showPdf(String path) {
		String lessonPath = "file://" + sdCard + path;
		Log.i(TAG, "lesson pdf path : " + lessonPath);
		Uri u = Uri.parse(lessonPath);
		SettingsManager.init(lessonPath);
		startDecoding(decodeService, sdCard + path, "");
		

		//lessonLayout.addView(documentView);

	}
	
	public void initMediaPlayer(List<Material> materialList) {
		smallMediaPlayer.insertMlist(materialList);
	}
	
	public void insertPic(Material material) {  //是向抽屉中添加图片。这个图片要控制一下大小再插入,主要是高度，在140-150之间，这个值我不是很确定。那个白色背景是方便调试的，使用时去掉。
			String path = sdCard + material.getPath();
			ImageView imageView = new ImageView(this);
			Log.d(TAG, "Material path : " + path);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			options.inPurgeable = true;
			File f = new File(path);
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			Log.i(TAG, "图片 高度:" + options.outHeight + " width: " + options.outWidth);
			int width = options.outWidth * 145 / options.outHeight;
			options.outHeight = 145;
			options.outWidth = width;
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,145);
			params.rightMargin = 10;
			imageView.setLayoutParams(params);
			options.inJustDecodeBounds = false;
			bm = BitmapFactory.decodeFile(path, options);
			imageView.setImageBitmap(bm);
			imageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					LayoutInflater inflater = LayoutInflater.from(arg0.getContext());
					View imgEntryView = inflater.inflate(R.layout.dialog_picture, null); // 加载自定义的布局文件
					final AlertDialog dialog = new AlertDialog.Builder(arg0.getContext()).create();
					ImageView imageV = (ImageView)imgEntryView.findViewById(R.id.large_image);
					imageV.setImageDrawable(((ImageView)arg0).getDrawable());
					dialog.setView(imgEntryView); // 自定义dialog
					dialog.show();
					imgEntryView.setOnClickListener(new OnClickListener() {
						public void onClick(View paramView) {
							dialog.cancel();
						}
					});
				}
			});
			picview.addView(imageView);

	}
	
	private void initDecodeService()
    {
        if (decodeService == null)
        {
            decodeService = new DecodeServiceBase(new PdfContext());
        }
    }
    
    @Override
    protected void onStop() {
    	recycleResource();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
    	System.out.println("OnDestroy");
        recycleResource();
        super.onDestroy();
    }
    
    public void onPanelClosed(Panel panel) {
    	panelHandle.setBackgroundResource(R.drawable.btn_drawer_up);
	}
	public void onPanelOpened(Panel panel) {
		panelHandle.setBackgroundResource(R.drawable.btn_drawer_down);
	}
	
	@Override
    public void onBackPressed() {
    	System.out.println("back pressed");
    	Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this,IndexActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

	@Override
	public void currentPageChanged(PageIndex oldIndex, PageIndex newIndex) {
		final int pageCount = documentModel.getPageCount();
        String prefix = "";

        if (pageCount > 0) {
            final String pageText = (newIndex.viewIndex + 1) + "/" + pageCount;
            if (SettingsManager.getAppSettings().getPageInTitle()) {
                prefix = "(" + pageText + ") ";
            } else {
                if (pageNumberToast != null) {
                    pageNumberToast.setText(pageText);
                } else {
                    pageNumberToast = Toast.makeText(this, pageText, 300);
                }
                pageNumberToast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                pageNumberToast.show();
            }
        }

        SettingsManager.currentPageChanged(oldIndex, newIndex);
		
	}

	@Override
    public void decodingProgressChanged(final int currentlyDecoding) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {
                    setProgressBarIndeterminateVisibility(true);
                    getWindow().setFeatureInt(Window.FEATURE_INDETERMINATE_PROGRESS,
                            currentlyDecoding == 0 ? 10000 : currentlyDecoding);
                } catch (final Throwable e) {
                }
            }
        });
    }

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public DecodeService getDecodeService() {
		return decodeService;
	}

	@Override
	public DocumentModel getDocumentModel() {
		return documentModel;
	}

	@Override
	public View getView() {
		return documentController.getView();
	}

	@Override
	public IDocumentViewController getDocumentController() {
		return documentController;
	}

	@Override
	public ZoomModel getZoomModel() {
		return zoomModel;
	}

	@Override
	public MultiTouchZoom getMultiTouchZoom() {
		return multiTouchZoom;
	}

	@Override
	public DecodingProgressModel getDecodingProgressModel() {
		return progressModel;
	}

	@Override
	public void createDocumentView() {
		System.out.println("Here goes");
		 if (documentController != null) {
	            lessonLayout.removeView(documentController.getView());
	            zoomModel.removeEventListener(documentController);
	        }


	        documentController = new ContiniousDocumentView(this);

	        zoomModel.addEventListener(documentController);
	        documentController.getView().setLayoutParams(
	                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

	        lessonLayout.addView(documentController.getView());
		
	}

	@Override
	public void onAppSettingsChanged(AppSettings oldSettings,
			AppSettings newSettings, Diff diff) {
		System.out.println("app setting");
	}

	@Override
	public void onBookSettingsChanged(BookSettings oldSettings,
			BookSettings newSettings,
			org.ebookdroid.core.settings.BookSettings.Diff diff, Diff appDiff) {
		// TODO Auto-generated method stub
		System.out.println("book setting changed");
		boolean redrawn = false;
        if (diff.isSinglePageChanged() || diff.isSplitPagesChanged()) {
            redrawn = true;
            createDocumentView();
        }

        if (diff.isZoomChanged() && diff.isFirstTime()) {
            redrawn = true;
            getZoomModel().setZoom(newSettings.getZoom());
        }

        final IDocumentViewController dc = getDocumentController();
        if (dc != null) {

            if (diff.isPageAlignChanged()) {
                dc.setAlign(newSettings.getPageAlign());
            }

            if (diff.isAnimationTypeChanged()) {
                dc.updateAnimationType();
            }

            if (!redrawn && appDiff != null) {
                if (appDiff.isMaxImageSizeChanged() || appDiff.isPagesInMemoryChanged()
                        || appDiff.isDecodeModeChanged()) {
                    dc.updateMemorySettings();
                }
            }
        }

        final DocumentModel dm = getDocumentModel();
        if (dm != null) {
            currentPageChanged(PageIndex.NULL, dm.getCurrentIndex());
        }
	}
	
}
