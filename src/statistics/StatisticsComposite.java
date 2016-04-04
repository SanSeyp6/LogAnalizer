package statistics;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import util.Util;

public class StatisticsComposite extends Composite {
	private Text text;
	private Text resultsText;
	private Text statusText;
	private String parsedJsonMessagesFileName;
	private String resultsFileName;
	private Map<String, Map<String, Integer>> statisticsMap;

	public StatisticsComposite(Composite parent, int style) {
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
		lblResultsJsonFile.setText("Results JSON File");

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
			statusText.setText("Invalid file names. Check file paths and provide the correct ones, then restart computations");
		}
		statusText.setText("Processing in progress...");
		getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					PerformanceUtil.initialize();
					JsonReadWriteUtils.readJsonLogFile(parsedJsonMessagesFileName);
					JSONArray jsonArray = JsonReadWriteUtils.getJsonArray();
					statisticsMap = computeStatistics(jsonArray);
					JSONArray outputArray = buildOutputJsonArray(statisticsMap);
					JsonReadWriteUtils.saveJsonArrayToFile(outputArray, resultsFileName);
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

	private Map<String, Map<String, Integer>> computeStatistics(JSONArray jsonArray) {
		// Пока шаблонов всего мешьне 150, но так, про запас
		Map<String, Map<String, Integer>> returnMap = new HashMap<String, Map<String, Integer>>(400); 
		Map<String, Integer> innerMap;
		JSONObject jsonObject, previousJsonObject = null;
		String template, previousTemplate;
		Integer amount;

		for (Object o : jsonArray) {
			jsonObject = (JSONObject) o;
			if (previousJsonObject == null) {
				previousJsonObject = jsonObject;
				continue;
			}
			template = (String) jsonObject.get("template");
			previousTemplate = (String) previousJsonObject.get("template");
			if (template != null && previousTemplate != null) {
				if (returnMap.containsKey(template)) {
					innerMap = returnMap.get(template);
					if (innerMap.containsKey(previousTemplate)) {
						amount = innerMap.get(previousTemplate);
						amount++;
						innerMap.put(previousTemplate, amount);
					} else {
						innerMap.put(previousTemplate, Integer.valueOf(1));
					}
				} else {
					innerMap = new HashMap<String, Integer>(3);
					innerMap.put(previousTemplate, Integer.valueOf(1));
					returnMap.put(template, innerMap);
				}
			} else {
				// добавить "неизвестное сообщение" ?
				// Пока нет, ибо неизвестная причина есть всегда =)
				// А нам нужны известные
			}
			previousJsonObject = jsonObject;
		}

		return returnMap;
	}

	private JSONArray buildOutputJsonArray(Map<String, Map<String, Integer>> statisticsMap) {
		JSONArray returnArray = new JSONArray();
		JSONArray innerArray;
		JSONObject jsonObject;
		JSONObject innerObject;

		for (Entry<String, Map<String, Integer>> entry : statisticsMap.entrySet()) {
			jsonObject = new JSONObject();
			jsonObject.put("template", entry.getKey());
			innerArray = new JSONArray();
			for (Entry<String, Integer> innerEntry : entry.getValue().entrySet()) {
				innerObject = new JSONObject();
				innerObject.put("previousTemplate", innerEntry.getKey());
				innerObject.put("amount", innerEntry.getValue());
				innerArray.add(innerObject);
			}
			jsonObject.put("previousTemplates", innerArray);
			returnArray.add(jsonObject);
		}

		return returnArray;
	}

}
