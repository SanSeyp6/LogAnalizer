package parse.standalone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import ru.hse.performance.PerformanceUtil;
import util.JsonReadWriteUtils;
import util.ParseMessage;
import util.Util;

public class ConvertToArffDataComposite extends Composite {
	private Text text;
	private Text resultsText;
	private Text statusText;
	private String parsedJsonMessagesFileName;
	private String resultsFileName;
	private Map<String, Map<String, Integer>> statisticsMap;

	public ConvertToArffDataComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));

		Label lblParsedJsonMessages = new Label(this, SWT.NONE);
		lblParsedJsonMessages.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblParsedJsonMessages.setText("Parsed JSON Messages File");

		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnExplore = new Button(this, SWT.NONE);
		btnExplore.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnExplore.setText("Explore");
		btnExplore.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parsedJsonMessagesFileName = new FileDialog(getShell()).open();
				if (parsedJsonMessagesFileName != null && !parsedJsonMessagesFileName.trim().isEmpty()) {
					text.setText(parsedJsonMessagesFileName);
				}
			}
		});

		Label lblResultsJsonFile = new Label(this, SWT.NONE);
		lblResultsJsonFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblResultsJsonFile.setText("Results ARFF Data File");

		resultsText = new Text(this, SWT.BORDER);
		resultsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		Button resultsSaveAs = new Button(this, SWT.NONE);
		resultsSaveAs.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		resultsSaveAs.setText("Save As");
		resultsSaveAs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resultsFileName = new FileDialog(getShell(), SWT.SAVE).open();
				if (resultsFileName != null && !resultsFileName.trim().isEmpty()) {
					resultsText.setText(resultsFileName);
				}
			}
		});

		Button runComputation = new Button(this, SWT.NONE);
		runComputation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				run();
			}
		});
		runComputation.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		runComputation.setText("Run statistics computation");

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));

		statusText = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		statusText.setEditable(false);
		statusText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
	}

	private void run() {
		if (parsedJsonMessagesFileName == null || parsedJsonMessagesFileName.trim().isEmpty() || resultsFileName == null
				|| resultsFileName.trim().isEmpty()) {
			statusText.setText(
					"Invalid file names. Check file paths and provide the correct ones, then restart computations");
		}
		statusText.setText("Processing in progress...");
		getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					PerformanceUtil.initialize();

					JsonReadWriteUtils.readJsonLogFile(parsedJsonMessagesFileName);
					JSONArray jsonArray = JsonReadWriteUtils.getJsonArray();
					List<String> arffData = convertToArffData(jsonArray);
					Files.write(Paths.get(resultsFileName), arffData, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

					statusText.setText("Finished!\n");
					PerformanceUtil.printTotalTime();
				} catch (IOException e) {
					statusText.setText(Util.getStackTrace(e));
					e.printStackTrace();
				} catch (ParseException e) {
					statusText.setText(Util.getStackTrace(e));
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @param jsonArray
	 * @ATTRIBUTE timegenerated-rfc-3339 date "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"
	 * @ATTRIBUTE timereported-rfc-3339 date "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"
	 * @ATTRIBUTE hostname string
	 * @ATTRIBUTE fromhost string
	 * @ATTRIBUTE fromhost-ip string
	 * @ATTRIBUTE syslogtag string
	 * @ATTRIBUTE programname string
	 * @ATTRIBUTE procid string
	 * @ATTRIBUTE app-name string
	 * @ATTRIBUTE pri-text string
	 * @ATTRIBUTE pri NUMERIC
	 * @ATTRIBUTE uuid string
	 * @ATTRIBUTE msgid string
	 * @ATTRIBUTE msg string
	 * @ATTRIBUTE template {}
	 * @ATTRIBUTE prev-template {}
	 * 
	 * @return ""
	 */

	private static String[] attributes = new String[] { "timegenerated-rfc-3339", "timereported-rfc-3339", "hostname",
			"fromhost", "fromhost-ip", "syslogtag", "programname", "procid", "app-name", "pri-text", "pri", "uuid",
			"msgid", "msg", "template", "prev-template" };

	private List<String> convertToArffData(JSONArray messages) {
		List<String> returnList = new LinkedList<>();
		JSONObject message, prevMessage=null;
		StringBuilder sb;

		
		for (Object o : messages) {
			message = (JSONObject) o;
			sb = new StringBuilder();
			for(int i=0; i< attributes.length; i++){
				switch (i) {
				case 0: case 1:
					sb.append('\"');
					sb.append(message.get(attributes[i]));
					sb.append("\",");
					break;
				case 10:
					sb.append(message.get(attributes[i]));
					sb.append("\",");
					break;
				case 14:
					if(message.get(attributes[i])!=null){
						sb.append('\'');
						sb.append(message.get(attributes[i]));
						sb.append("\',");
					} else {
						sb.append("\'\',");
					}
					break;
				case 15:
					if(prevMessage != null){
						sb.append('\'');
						sb.append(prevMessage.get(attributes[14])==null? "" : prevMessage.get(attributes[14]));
						sb.append("\'");
					} else {
						sb.append("\'\'");
					}
					break;
				default:
					sb.append('\'');
					sb.append(message.get(attributes[i]));
					sb.append("\',");
					break;
				}
			}
			prevMessage = message;
			returnList.add(sb.toString());
		}

		return returnList;
	}

}
