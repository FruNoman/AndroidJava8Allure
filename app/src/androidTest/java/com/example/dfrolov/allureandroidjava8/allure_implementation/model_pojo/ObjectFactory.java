
package com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the io.qameta.allure.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TestRunResult_QNAME = new QName("urn:model.allure.qameta.io", "testRunResult");
    private final static QName _TestResultContainer_QNAME = new QName("urn:model.allure.qameta.io", "testResultContainer");
    private final static QName _TestResult_QNAME = new QName("urn:model.allure.qameta.io", "testResult");
    private final static QName _StepResult_QNAME = new QName("urn:model.allure.qameta.io", "stepResult");
    private final static QName _FixtureResult_QNAME = new QName("urn:model.allure.qameta.io", "fixtureResult");
    private final static QName _Attachment_QNAME = new QName("urn:model.allure.qameta.io", "attachment");
    private final static QName _Parameter_QNAME = new QName("urn:model.allure.qameta.io", "parameter");
    private final static QName _Label_QNAME = new QName("urn:model.allure.qameta.io", "label");
    private final static QName _Link_QNAME = new QName("urn:model.allure.qameta.io", "link");
    private final static QName _StatusDetails_QNAME = new QName("urn:model.allure.qameta.io", "statusDetails");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: io.qameta.allure.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TestRunResult }
     * 
     */
    public TestRunResult createTestRunResult() {
        return new TestRunResult();
    }

    /**
     * Create an instance of {@link TestResultContainer }
     *
     */
    public TestResultContainer createTestResultContainer() {
        return new TestResultContainer();
    }

    /**
     * Create an instance of {@link TestResult }
     *
     */
    public TestResult createTestResult() {
        return new TestResult();
    }

    /**
     * Create an instance of {@link StepResult }
     *
     */
    public StepResult createStepResult() {
        return new StepResult();
    }

    /**
     * Create an instance of {@link FixtureResult }
     *
     */
    public FixtureResult createFixtureResult() {
        return new FixtureResult();
    }

    /**
     * Create an instance of {@link Attachment }
     *
     */
    public Attachment createAttachment() {
        return new Attachment();
    }

    /**
     * Create an instance of {@link Parameter }
     *
     */
    public Parameter createParameter() {
        return new Parameter();
    }

    /**
     * Create an instance of {@link Label }
     *
     */
    public Label createLabel() {
        return new Label();
    }

    /**
     * Create an instance of {@link Link }
     *
     */
    public Link createLink() {
        return new Link();
    }

    /**
     * Create an instance of {@link StatusDetails }
     *
     */
    public StatusDetails createStatusDetails() {
        return new StatusDetails();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestRunResult }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "urn:model.allure.qameta.io", name = "testRunResult")
    public JAXBElement<TestRunResult> createTestRunResult(TestRunResult value) {
        return new JAXBElement<TestRunResult>(_TestRunResult_QNAME, TestRunResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestResultContainer }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "urn:model.allure.qameta.io", name = "testResultContainer")
    public JAXBElement<TestResultContainer> createTestResultContainer(TestResultContainer value) {
        return new JAXBElement<TestResultContainer>(_TestResultContainer_QNAME, TestResultContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestResult }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "urn:model.allure.qameta.io", name = "testResult")
    public JAXBElement<TestResult> createTestResult(TestResult value) {
        return new JAXBElement<TestResult>(_TestResult_QNAME, TestResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StepResult }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "urn:model.allure.qameta.io", name = "stepResult")
    public JAXBElement<StepResult> createStepResult(StepResult value) {
        return new JAXBElement<StepResult>(_StepResult_QNAME, StepResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FixtureResult }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "urn:model.allure.qameta.io", name = "fixtureResult")
    public JAXBElement<FixtureResult> createFixtureResult(FixtureResult value) {
        return new JAXBElement<FixtureResult>(_FixtureResult_QNAME, FixtureResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Attachment }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "urn:model.allure.qameta.io", name = "attachment")
    public JAXBElement<Attachment> createAttachment(Attachment value) {
        return new JAXBElement<Attachment>(_Attachment_QNAME, Attachment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Parameter }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "urn:model.allure.qameta.io", name = "parameter")
    public JAXBElement<Parameter> createParameter(Parameter value) {
        return new JAXBElement<Parameter>(_Parameter_QNAME, Parameter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Label }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "urn:model.allure.qameta.io", name = "label")
    public JAXBElement<Label> createLabel(Label value) {
        return new JAXBElement<Label>(_Label_QNAME, Label.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Link }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "urn:model.allure.qameta.io", name = "link")
    public JAXBElement<Link> createLink(Link value) {
        return new JAXBElement<Link>(_Link_QNAME, Link.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusDetails }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "urn:model.allure.qameta.io", name = "statusDetails")
    public JAXBElement<StatusDetails> createStatusDetails(StatusDetails value) {
        return new JAXBElement<StatusDetails>(_StatusDetails_QNAME, StatusDetails.class, null, value);
    }

}
