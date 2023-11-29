package com.example.Seulah.service;

import com.example.Seulah.entity.EligibilityQuestionSet;
import com.example.Seulah.entity.EligibilityQuestions;
import com.example.Seulah.entity.QuestionSet;
import com.example.Seulah.repository.EligibilityQuestionSetRepository;
import com.example.Seulah.repository.EligibilityQuestionsRepository;
import com.example.Seulah.repository.QuestionSetRepository;
import com.example.Seulah.request.MessageResponse;
import com.example.Seulah.request.QuestionSetResponse;
import com.example.Seulah.request.QuestionValuePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EligibilityQuestionSetService {
    private final EligibilityQuestionSetRepository eligibilityQuestionSetRepository;
    private final EligibilityQuestionsRepository eligibilityQuestionsRepository;

    private final QuestionSetRepository questionSetRepository;

    public EligibilityQuestionSetService(EligibilityQuestionSetRepository eligibilityQuestionSetRepository, EligibilityQuestionsRepository eligibilityQuestionsRepository, QuestionSetRepository questionSetRepository) {
        this.eligibilityQuestionSetRepository = eligibilityQuestionSetRepository;
        this.eligibilityQuestionsRepository = eligibilityQuestionsRepository;
        this.questionSetRepository = questionSetRepository;
    }

    @Transactional
    public ResponseEntity<MessageResponse> saveQuestion(String setName, List<Long> questionIds) {
        EligibilityQuestionSet eligibilityQuestionSet = new EligibilityQuestionSet();
        eligibilityQuestionSet.setName(setName);

        List<EligibilityQuestions> eligibilityQuestions = eligibilityQuestionsRepository.findAllById(questionIds);
        EligibilityQuestionSet finalEligibilityQuestionSet = eligibilityQuestionSet;

        eligibilityQuestions.forEach(eligibilityQuestion -> {
            QuestionSet questionSet = new QuestionSet();
            questionSet.setQuestion(eligibilityQuestion.getQuestion());
            questionSet.setEligibilityQuestionSet(finalEligibilityQuestionSet);

            finalEligibilityQuestionSet.getQuestions().add(questionSet);
        });

        eligibilityQuestionSet = eligibilityQuestionSetRepository.save(eligibilityQuestionSet);
        questionSetRepository.saveAll(eligibilityQuestionSet.getQuestions());

        return new ResponseEntity<>(new MessageResponse("Set Created Successfully", eligibilityQuestionSet, false), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> getQuestionById(Long id) {
        Optional<EligibilityQuestionSet> eligibilityQuestionSet = eligibilityQuestionSetRepository.findById(id);

        if (eligibilityQuestionSet.isPresent()) {
            List<Map<String, Object>> questionsWithOptions = new ArrayList<>();

            eligibilityQuestionSet.get().getQuestions().forEach(question -> {
                EligibilityQuestions eligibilityQuestion = eligibilityQuestionsRepository.findByQuestion(question.getQuestion());
                if (eligibilityQuestion != null) {
                    Map<String, Object> questionWithOptions = new HashMap<>();
                    questionWithOptions.put("id", question.getId());
                    questionWithOptions.put("answer", question.getAnswer());
                    questionWithOptions.put("question", question.getQuestion());
                    questionWithOptions.put("options", eligibilityQuestion.getOptions());
                    questionWithOptions.put("Heading", eligibilityQuestion.getHeading());

                    questionsWithOptions.add(questionWithOptions);
                }
            });

            return new ResponseEntity<>(new MessageResponse("Success", questionsWithOptions, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<MessageResponse> getQuestions() {
        List<EligibilityQuestionSet> eligibilityQuestionSets = eligibilityQuestionSetRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", eligibilityQuestionSets, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteQuestion(Long id) {
        Optional<EligibilityQuestionSet> eligibilityQuestionSet = eligibilityQuestionSetRepository.findById(id);
        if (eligibilityQuestionSet.isPresent()) {
            eligibilityQuestionSetRepository.delete(eligibilityQuestionSet.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<MessageResponse> updateAnswer(String setName, Long setId, Long questionId, List<String> answer) {
        Optional<EligibilityQuestionSet> optionalEligibilityQuestionSet = eligibilityQuestionSetRepository.findById(setId);

        if (optionalEligibilityQuestionSet.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
        }
        EligibilityQuestionSet eligibilityQuestionSet = optionalEligibilityQuestionSet.get();
        Optional<QuestionSet> optionalQuestionSet = questionSetRepository.findByIdWithEligibilityQuestions(questionId);

        if (optionalQuestionSet.isPresent()) {
            QuestionSet questionSet = optionalQuestionSet.get();
            if (setName != null && !setName.isEmpty()) {
                eligibilityQuestionSet.setName(setName);
            }
            questionSet.setAnswer(answer);

            eligibilityQuestionSetRepository.save(eligibilityQuestionSet);
            questionSetRepository.save(questionSet);
            List<EligibilityQuestionSet> eligibilityQuestionSets = eligibilityQuestionSetRepository.findAllByCustomOrder();
            return new ResponseEntity<>(new MessageResponse("Answer Updated Successfully", eligibilityQuestionSets, false), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("QuestionSet not found with id: " + questionId, null, false), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<MessageResponse> getQuestionByIdAndSetId(Long questionId, Long setId) {
        Optional<EligibilityQuestionSet> eligibilityQuestionSet = eligibilityQuestionSetRepository.findById(setId);
        AtomicReference<Map<String, Object>> responseData = new AtomicReference<>(new HashMap<>());

        eligibilityQuestionSet.ifPresent(questionSet -> questionSet.getQuestions().forEach(question -> {
            if (question.getId().equals(questionId) && question.getQuestion() != null && !question.getQuestion().isEmpty()) {
                responseData.get().put("id", question.getId());
                responseData.get().put("question", question.getQuestion());
                EligibilityQuestions eligibilityQuestions = eligibilityQuestionsRepository.findByQuestion(question.getQuestion());
                if (eligibilityQuestions != null) {
                    responseData.get().put("Option", eligibilityQuestions.getOptions());
                }
            }
        }));

        if (!responseData.get().isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("Success", responseData.get(), false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> getFormulaByEligibilityQuestionSetId(Long eligibilityQuestionSetId) {
        Optional<EligibilityQuestionSet> eligibilityQuestionSetOptional = eligibilityQuestionSetRepository.findById(eligibilityQuestionSetId);

        if (eligibilityQuestionSetOptional.isPresent()) {
            EligibilityQuestionSet eligibilityQuestionSet = eligibilityQuestionSetOptional.get();
            if (eligibilityQuestionSet.getFormula() != null) {
                return new ResponseEntity<>(new MessageResponse("Success", eligibilityQuestionSet.getFormula(), false), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getQuestionSetByNumericAndString(Long id, Boolean forUser) {
        Optional<EligibilityQuestionSet> eligibilityQuestionSet = eligibilityQuestionSetRepository.findById(id);
        Map<String, Object> responseData = new HashMap<>();
        List<Object> numericQuestions = new ArrayList<>();
        List<Object> textQuestions = new ArrayList<>();
        Set<QuestionValuePair> otherQuestions = new HashSet<>();

        eligibilityQuestionSet.ifPresent(questionSet -> {
            responseData.put("Formula", eligibilityQuestionSet.get().getFormula());
            Set<Long> processedNumericQuestionIds = new HashSet<>();
            Set<Long> processedTextQuestionIds = new HashSet<>();
            Set<Long> processedOtherQuestionIds = new HashSet<>();

            questionSet.getQuestions().forEach(question -> {
                String questionText = question.getQuestion();
                EligibilityQuestions eligibilityQuestions = eligibilityQuestionsRepository.findByQuestion(questionText);

                if (eligibilityQuestions != null) {
                    for (String option : eligibilityQuestions.getOptions()) {
                        String optionType = option.toLowerCase();

                        if (optionType.equals("numeric")) {
                            if (processedNumericQuestionIds.add(question.getId())) {
                                numericQuestions.add(new EligibilityQuestions(question.getId(), eligibilityQuestions.getHeading(), eligibilityQuestions.getQuestion(), eligibilityQuestions.getType(), eligibilityQuestions.getOptions()));
                            }
                        } else if (optionType.equals("text")) {
                            if (processedTextQuestionIds.add(question.getId())) {
                                textQuestions.add(new EligibilityQuestions(question.getId(), eligibilityQuestions.getHeading(), eligibilityQuestions.getQuestion(), eligibilityQuestions.getType(), eligibilityQuestions.getOptions()));
                            }
                        } else {
                            if (processedOtherQuestionIds.add(question.getId())) {
                                if (forUser) {
                                    QuestionValuePair otherData = new QuestionValuePair(new EligibilityQuestions(question.getId(), eligibilityQuestions.getHeading(), eligibilityQuestions.getQuestion(), eligibilityQuestions.getType(), eligibilityQuestions.getOptions()), null);
                                    otherQuestions.add(otherData);
                                } else {
                                    QuestionValuePair otherData = new QuestionValuePair(new EligibilityQuestions(question.getId(), eligibilityQuestions.getHeading(), eligibilityQuestions.getQuestion(), eligibilityQuestions.getType(), eligibilityQuestions.getOptions()), question.getAnswer());
                                    otherQuestions.add(otherData);
                                }
                            }
                        }
                    }
                }
            });
        });


        responseData.put("Numeric_Question", numericQuestions);
        responseData.put("Text_Question", textQuestions);
        responseData.put("Other_Question", otherQuestions);

        return new ResponseEntity<>(new MessageResponse("Success", responseData, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getAllDecision() {
        Map<String, QuestionSetResponse> questionSetResponsesMap = new HashMap<>();

        List<EligibilityQuestionSet> allQuestionSets = eligibilityQuestionSetRepository.findAll();

        allQuestionSets.forEach(questionSet -> {
            Long setId = questionSet.getId();
            String setName = questionSet.getName();
            Object formula = questionSet.getFormula() != null ? questionSet.getFormula() : null;

            QuestionSetResponse questionSetResponse = questionSetResponsesMap.computeIfAbsent(setId.toString(), key -> new QuestionSetResponse(setId, formula, setName));

            Set<String> processedNumericQuestions = new HashSet<>();
            Set<String> processedTextQuestions = new HashSet<>();
            Set<String> processedOtherQuestions = new HashSet<>();
            Set<String> processedQuestionKeys = new HashSet<>();

            questionSet.getQuestions().forEach(question -> {
                String questionKey = question.getQuestion() + "_" + setId;
                if (!processedQuestionKeys.contains(questionKey)) {
                    processedQuestionKeys.add(questionKey);

                    EligibilityQuestions eligibilityQuestions = eligibilityQuestionsRepository.findByQuestion(question.getQuestion());
                    if (eligibilityQuestions != null) {
                        for (String option : eligibilityQuestions.getOptions()) {
                            String optionType = option.toLowerCase();

                            // Create a new instance for each question and add to the appropriate list in QuestionSetResponse
                            QuestionValuePair questionValuePair = new QuestionValuePair(new EligibilityQuestions(question.getId(), eligibilityQuestions.getHeading(), eligibilityQuestions.getQuestion(), eligibilityQuestions.getType(), eligibilityQuestions.getOptions()), optionType.equals("other") ? question.getAnswer() : null);

                            switch (optionType) {
                                case "numeric":
                                    if (!processedNumericQuestions.contains(questionKey)) {
                                        processedNumericQuestions.add(questionKey);
                                        questionSetResponse.getNumericQuestions().add(questionValuePair);
                                    }
                                    break;
                                case "text":
                                    if (!processedTextQuestions.contains(questionKey)) {
                                        processedTextQuestions.add(questionKey);
                                        questionSetResponse.getTextQuestions().add(questionValuePair);
                                    }
                                    break;
                                default:
                                    if (!processedOtherQuestions.contains(questionKey)) {
                                        processedOtherQuestions.add(questionKey);
                                        questionSetResponse.getOtherQuestions().add(questionValuePair);
                                    }
                                    break;
                            }
                        }
                    }
                }
            });
            questionSetResponsesMap.put(setId.toString(), questionSetResponse);
        });

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("QuestionSets", new ArrayList<>(questionSetResponsesMap.values())); 

        return new ResponseEntity<>(new MessageResponse("Success", responseData, false), HttpStatus.OK);
    }

}


