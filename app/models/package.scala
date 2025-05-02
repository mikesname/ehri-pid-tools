import play.api.libs.json.{Json, Reads}

package object models {
  /**
   * Scala case classes representing DataCite v4.7 DOI metadata schema
   * Based on: https://schema.datacite.org/meta/kernel-4.7/
   */


  /**
   * Main class representing a complete DataCite metadata record
   */
  case class DataCiteMetadata(
    identifiers: Seq[Identifier],
    creators: Seq[Creator],
    titles: Seq[Title],
    publisher: Option[String],
    publicationYear: Option[Int],
    types: ResourceType,
    subjects: Option[Seq[Subject]] = None,
    contributors: Option[Seq[Contributor]] = None,
    dates: Option[Seq[Date]] = None,
    language: Option[String] = None,
    alternateIdentifiers: Option[Seq[AlternateIdentifier]] = None,
    relatedIdentifiers: Option[Seq[RelatedIdentifier]] = None,
    sizes: Option[Seq[String]] = None,
    formats: Option[Seq[String]] = None,
    version: Option[String] = None,
    rightsSeq: Option[Seq[Rights]] = None,
    descriptions: Option[Seq[Description]] = None,
    geoLocations: Option[Seq[GeoLocation]] = None,
    fundingReferences: Option[Seq[FundingReference]] = None,
    relatedItems: Option[Seq[RelatedItem]] = None
  ) {
    def title: Option[String] = titles.map(_.title).headOption
  }

  object DataCiteMetadata {
    private implicit def _identifierReads: Reads[Identifier] = Json.reads[Identifier]
    private implicit def _nameIdentifierReads: Reads[NameIdentifier] = Json.reads[NameIdentifier]
    private implicit def _funderIdentifierReads: Reads[FunderIdentifier] = Json.reads[FunderIdentifier]
    private implicit def _fundingReferenceReads: Reads[FundingReference] = Json.reads[FundingReference]
    private implicit def _affiliationReads: Reads[Affiliation] = Json.reads[Affiliation]
    private implicit def _creatorReads: Reads[Creator] = Json.reads[Creator]
    private implicit def _titleReads: Reads[Title] = Json.reads[Title]
    private implicit def _resourceTypeReads: Reads[ResourceType] = Json.reads[ResourceType]
    private implicit def _subjectReads: Reads[Subject] = Json.reads[Subject]
    private implicit def _contributorReads: Reads[Contributor] = Json.reads[Contributor]
    private implicit def _dateReads: Reads[Date] = Json.reads[Date]
    private implicit def _alternateIdentifierReads: Reads[AlternateIdentifier] = Json.reads[AlternateIdentifier]
    private implicit def _relatedIdentifierReads: Reads[RelatedIdentifier] = Json.reads[RelatedIdentifier]
    private implicit def _rightsReads: Reads[Rights] = Json.reads[Rights]
    private implicit def _descriptionReads: Reads[Description] = Json.reads[Description]
    private implicit def _geoLocationReads: Reads[GeoLocation] = Json.reads[GeoLocation]
    private implicit def _geoLocationPointReads: Reads[GeoLocationPoint] = Json.reads[GeoLocationPoint]
    private implicit def _geoLocationBoxReads: Reads[GeoLocationBox] = Json.reads[GeoLocationBox]
    private implicit def _geoLocationPolygonReads: Reads[GeoLocationPolygon] = Json.reads[GeoLocationPolygon]
    private implicit def _relatedItemReads: Reads[RelatedItem] = Json.reads[RelatedItem]
    private implicit def _relatedItemIdentifierReads: Reads[RelatedItemIdentifier] = Json.reads[RelatedItemIdentifier]
    implicit val _reads: Reads[DataCiteMetadata] = Json.reads[DataCiteMetadata]
  }

  /**
   * Represents the main DOI identifier
   */
  case class Identifier(
    identifier: String,
    identifierType: String = "DOI"
  )

  /**
   * Represents a creator (author)
   */
  case class Creator(
    name: Option[String] = None,
    nameType: Option[String] = None,
    givenName: Option[String] = None,
    familyName: Option[String] = None,
    nameIdentifiers: Option[List[NameIdentifier]] = None,
    affiliations: Option[List[Affiliation]] = None
  )

  /**
   * Represents a title
   */
  case class Title(
    title: String,
    titleType: Option[String] = None,
    lang: Option[String] = None
  )

  /**
   * Represents a resource type
   */
  case class ResourceType(
    resourceTypeGeneral: String,
    resourceType: Option[String]
  )

  /**
   * Represents a subject
   */
  case class Subject(
    subject: String,
    subjectScheme: Option[String] = None,
    schemeURI: Option[String] = None,
    valueURI: Option[String] = None,
    classificationCode: Option[String] = None,
    lang: Option[String] = None
  )

  /**
   * Represents a contributor
   */
  case class Contributor(
    name: String,
    nameType: Option[String] = None,
    givenName: Option[String] = None,
    familyName: Option[String] = None,
    nameIdentifiers: Option[List[NameIdentifier]] = None,
    affiliations: Option[List[Affiliation]] = None,
    contributorType: String
  )

  /**
   * Represents a date
   */
  case class Date(
    date: String, // ISO8601 date format
    dateType: String,
    dateInformation: Option[String] = None
  )

  /**
   * Represents an alternate identifier
   */
  case class AlternateIdentifier(
    alternateIdentifier: String,
    alternateIdentifierType: String
  )

  /**
   * Represents a related identifier
   */
  case class RelatedIdentifier(
    relatedIdentifier: String,
    relatedIdentifierType: String,
    relationType: String,
    resourceTypeGeneral: Option[String] = None,
    relatedMetadataScheme: Option[String] = None,
    schemeURI: Option[String] = None,
    schemeType: Option[String] = None
  )

  /**
   * Represents rights information
   */
  case class Rights(
    value: String,
    rightsIdentifier: Option[String] = None,
    rightsIdentifierScheme: Option[String] = None,
    schemeURI: Option[String] = None,
    rightsURI: Option[String] = None,
    lang: Option[String] = None
  )

  /**
   * Represents a description
   */
  case class Description(
    description: String,
    descriptionType: Option[String],
    lang: Option[String] = None
  )

  /**
   * Represents a geographic location
   */
  case class GeoLocation(
    geoLocationPoint: Option[GeoLocationPoint] = None,
    geoLocationBox: Option[GeoLocationBox] = None,
    geoLocationPlace: Option[String] = None,
    geoLocationPolygon: Option[List[GeoLocationPolygon]] = None
  )

  /**
   * Represents a geographical point location
   */
  case class GeoLocationPoint(
    pointLongitude: Double,
    pointLatitude: Double
  )

  /**
   * Represents a geographical box area
   */
  case class GeoLocationBox(
    westBoundLongitude: Double,
    eastBoundLongitude: Double,
    southBoundLatitude: Double,
    northBoundLatitude: Double
  )

  /**
   * Represents a geographical polygon
   */
  case class GeoLocationPolygon(
    polygonPoints: List[GeoLocationPoint],
    inPolygonPoint: Option[GeoLocationPoint] = None
  )

  /**
   * Represents a funding reference
   */
  case class FundingReference(
    funderName: String,
    funderIdentifier: Option[FunderIdentifier] = None,
    awardNumber: Option[String] = None,
    awardURI: Option[String] = None,
    awardTitle: Option[String] = None
  )

  /**
   * Represents a funder identifier
   */
  case class FunderIdentifier(
    value: String,
    funderIdentifierType: String,
    schemeURI: Option[String] = None
  )

  /**
   * Represents a related item
   */
  case class RelatedItem(
    relatedItemType: String,
    relationType: String,
    relatedItemIdentifier: Option[RelatedItemIdentifier] = None,
    creators: Option[List[Creator]] = None,
    titles: Option[List[Title]] = None,
    publicationYear: Option[Int] = None,
    volume: Option[String] = None,
    issue: Option[String] = None,
    number: Option[String] = None,
    numberType: Option[String] = None,
    firstPage: Option[String] = None,
    lastPage: Option[String] = None,
    publisher: Option[String] = None,
    edition: Option[String] = None,
    contributors: Option[List[Contributor]] = None
  )

  /**
   * Represents a related item identifier
   */
  case class RelatedItemIdentifier(
    value: String,
    relatedItemIdentifierType: String,
    relatedMetadataScheme: Option[String] = None,
    schemeURI: Option[String] = None,
    schemeType: Option[String] = None
  )

  /**
   * Represents a name identifier
   */
  case class NameIdentifier(
    nameIdentifier: String,
    nameIdentifierScheme: String,
    schemeURI: Option[String] = None
  )

  /**
   * Represents an affiliation
   */
  case class Affiliation(
    name: String,
    affiliationIdentifier: Option[String] = None,
    affiliationIdentifierScheme: Option[String] = None,
    schemeURI: Option[String] = None
  )

  // Enumerations for controlled vocabularies

//  /**
//   * Name type enumeration
//   */
//  sealed trait NameType
//  object NameType {
//    case object Organizational extends NameType
//    case object Personal extends NameType
//  }
//
//  /**
//   * Title type enumeration
//   */
//  sealed trait TitleType
//  object TitleType {
//    case object AlternativeTitle extends TitleType
//    case object Subtitle extends TitleType
//    case object TranslatedTitle extends TitleType
//    case object Other extends TitleType
//  }
//
//  /**
//   * Resource type general enumeration
//   */
//  sealed trait ResourceTypeGeneral
//  object ResourceTypeGeneral {
//    case object Audiovisual extends ResourceTypeGeneral
//    case object Book extends ResourceTypeGeneral
//    case object BookChapter extends ResourceTypeGeneral
//    case object Collection extends ResourceTypeGeneral
//    case object ComputationalNotebook extends ResourceTypeGeneral
//    case object ConferencePaper extends ResourceTypeGeneral
//    case object ConferenceProceeding extends ResourceTypeGeneral
//    case object DataPaper extends ResourceTypeGeneral
//    case object Dataset extends ResourceTypeGeneral
//    case object Dissertation extends ResourceTypeGeneral
//    case object Event extends ResourceTypeGeneral
//    case object Image extends ResourceTypeGeneral
//    case object InteractiveResource extends ResourceTypeGeneral
//    case object Journal extends ResourceTypeGeneral
//    case object JournalArticle extends ResourceTypeGeneral
//    case object Model extends ResourceTypeGeneral
//    case object OutputManagementPlan extends ResourceTypeGeneral
//    case object PeerReview extends ResourceTypeGeneral
//    case object PhysicalObject extends ResourceTypeGeneral
//    case object Preprint extends ResourceTypeGeneral
//    case object Report extends ResourceTypeGeneral
//    case object Service extends ResourceTypeGeneral
//    case object Software extends ResourceTypeGeneral
//    case object Sound extends ResourceTypeGeneral
//    case object Standard extends ResourceTypeGeneral
//    case object Text extends ResourceTypeGeneral
//    case object Workflow extends ResourceTypeGeneral
//    case object Other extends ResourceTypeGeneral
//  }
//
//  /**
//   * Contributor type enumeration
//   */
//  sealed trait ContributorType
//  object ContributorType {
//    case object ContactPerson extends ContributorType
//    case object DataCollector extends ContributorType
//    case object DataCurator extends ContributorType
//    case object DataManager extends ContributorType
//    case object Distributor extends ContributorType
//    case object Editor extends ContributorType
//    case object HostingInstitution extends ContributorType
//    case object Producer extends ContributorType
//    case object ProjectLeader extends ContributorType
//    case object ProjectManager extends ContributorType
//    case object ProjectMember extends ContributorType
//    case object RegistrationAgency extends ContributorType
//    case object RegistrationAuthority extends ContributorType
//    case object RelatedPerson extends ContributorType
//    case object Researcher extends ContributorType
//    case object ResearchGroup extends ContributorType
//    case object RightsHolder extends ContributorType
//    case object Sponsor extends ContributorType
//    case object Supervisor extends ContributorType
//    case object WorkPackageLeader extends ContributorType
//    case object Other extends ContributorType
//  }
//
//  /**
//   * Date type enumeration
//   */
//  sealed trait DateType
//  object DateType {
//    case object Accepted extends DateType
//    case object Available extends DateType
//    case object Copyrighted extends DateType
//    case object Collected extends DateType
//    case object Created extends DateType
//    case object Issued extends DateType
//    case object Submitted extends DateType
//    case object Updated extends DateType
//    case object Valid extends DateType
//    case object Withdrawn extends DateType
//    case object Other extends DateType
//  }
//
//  /**
//   * Related identifier type enumeration
//   */
//  sealed trait RelatedIdentifierType
//  object RelatedIdentifierType {
//    case object ARK extends RelatedIdentifierType
//    case object arXiv extends RelatedIdentifierType
//    case object bibcode extends RelatedIdentifierType
//    case object DOI extends RelatedIdentifierType
//    case object EAN13 extends RelatedIdentifierType
//    case object EISSN extends RelatedIdentifierType
//    case object Handle extends RelatedIdentifierType
//    case object IGSN extends RelatedIdentifierType
//    case object ISBN extends RelatedIdentifierType
//    case object ISSN extends RelatedIdentifierType
//    case object ISTC extends RelatedIdentifierType
//    case object LISSN extends RelatedIdentifierType
//    case object LSID extends RelatedIdentifierType
//    case object PMID extends RelatedIdentifierType
//    case object PURL extends RelatedIdentifierType
//    case object RAiD extends RelatedIdentifierType
//    case object ROR extends RelatedIdentifierType
//    case object RRID extends RelatedIdentifierType
//    case object UPC extends RelatedIdentifierType
//    case object URL extends RelatedIdentifierType
//    case object URN extends RelatedIdentifierType
//    case object w3id extends RelatedIdentifierType
//    case object Other extends RelatedIdentifierType
//  }
//
//  /**
//   * Related item identifier type enumeration
//   */
//  sealed trait RelatedItemIdentifierType
//  object RelatedItemIdentifierType {
//    case object ARK extends RelatedItemIdentifierType
//    case object arXiv extends RelatedItemIdentifierType
//    case object bibcode extends RelatedItemIdentifierType
//    case object DOI extends RelatedItemIdentifierType
//    case object EAN13 extends RelatedItemIdentifierType
//    case object EISSN extends RelatedItemIdentifierType
//    case object Handle extends RelatedItemIdentifierType
//    case object IGSN extends RelatedItemIdentifierType
//    case object ISBN extends RelatedItemIdentifierType
//    case object ISSN extends RelatedItemIdentifierType
//    case object ISTC extends RelatedItemIdentifierType
//    case object LISSN extends RelatedItemIdentifierType
//    case object LSID extends RelatedItemIdentifierType
//    case object PMID extends RelatedItemIdentifierType
//    case object PURL extends RelatedItemIdentifierType
//    case object RAiD extends RelatedItemIdentifierType
//    case object ROR extends RelatedItemIdentifierType
//    case object RRID extends RelatedItemIdentifierType
//    case object UPC extends RelatedItemIdentifierType
//    case object URL extends RelatedItemIdentifierType
//    case object URN extends RelatedItemIdentifierType
//    case object w3id extends RelatedItemIdentifierType
//    case object Other extends RelatedItemIdentifierType
//  }
//
//  /**
//   * Relation type enumeration
//   */
//  sealed trait RelationType
//  object RelationType {
//    case object IsCitedBy extends RelationType
//    case object Cites extends RelationType
//    case object IsSupplementTo extends RelationType
//    case object IsSupplementedBy extends RelationType
//    case object IsContinuedBy extends RelationType
//    case object Continues extends RelationType
//    case object IsDescribedBy extends RelationType
//    case object Describes extends RelationType
//    case object HasMetadata extends RelationType
//    case object IsMetadataFor extends RelationType
//    case object HasVersion extends RelationType
//    case object IsVersionOf extends RelationType
//    case object IsNewVersionOf extends RelationType
//    case object IsPreviousVersionOf extends RelationType
//    case object IsPartOf extends RelationType
//    case object HasPart extends RelationType
//    case object IsPublishedIn extends RelationType
//    case object IsReferencedBy extends RelationType
//    case object References extends RelationType
//    case object IsDocumentedBy extends RelationType
//    case object Documents extends RelationType
//    case object IsCompiledBy extends RelationType
//    case object Compiles extends RelationType
//    case object IsVariantFormOf extends RelationType
//    case object IsOriginalFormOf extends RelationType
//    case object IsIdenticalTo extends RelationType
//    case object IsReviewedBy extends RelationType
//    case object Reviews extends RelationType
//    case object IsDerivedFrom extends RelationType
//    case object IsSourceOf extends RelationType
//    case object IsRequiredBy extends RelationType
//    case object Requires extends RelationType
//    case object IsObsoletedBy extends RelationType
//    case object Obsoletes extends RelationType
//    case object IsCollectedBy extends RelationType
//    case object Collects extends RelationType
//  }
//
//  /**
//   * Description type enumeration
//   */
//  sealed trait DescriptionType
//  object DescriptionType {
//    case object Abstract extends DescriptionType
//    case object Methods extends DescriptionType
//    case object SeriesInformation extends DescriptionType
//    case object TableOfContents extends DescriptionType
//    case object TechnicalInfo extends DescriptionType
//    case object Other extends DescriptionType
//  }
//
//  /**
//   * Funder identifier type enumeration
//   */
//  sealed trait FunderIdentifierType
//  object FunderIdentifierType {
//    case object ROR extends FunderIdentifierType
//    case object GRID extends FunderIdentifierType
//    case object Crossref extends FunderIdentifierType
//    case object DOI extends FunderIdentifierType
//    case object ISNI extends FunderIdentifierType
//    case object Other extends FunderIdentifierType
//  }
//
//  /**
//   * Related item type enumeration
//   */
//  sealed trait RelatedItemType
//  object RelatedItemType {
//    case object Audiovisual extends RelatedItemType
//    case object Book extends RelatedItemType
//    case object BookChapter extends RelatedItemType
//    case object Collection extends RelatedItemType
//    case object ComputationalNotebook extends RelatedItemType
//    case object ConferencePaper extends RelatedItemType
//    case object ConferenceProceeding extends RelatedItemType
//    case object DataPaper extends RelatedItemType
//    case object Dataset extends RelatedItemType
//    case object Dissertation extends RelatedItemType
//    case object Event extends RelatedItemType
//    case object Image extends RelatedItemType
//    case object InteractiveResource extends RelatedItemType
//    case object Journal extends RelatedItemType
//    case object JournalArticle extends RelatedItemType
//    case object Model extends RelatedItemType
//    case object OutputManagementPlan extends RelatedItemType
//    case object PeerReview extends RelatedItemType
//    case object PhysicalObject extends RelatedItemType
//    case object Preprint extends RelatedItemType
//    case object Report extends RelatedItemType
//    case object Service extends RelatedItemType
//    case object Software extends RelatedItemType
//    case object Sound extends RelatedItemType
//    case object Standard extends RelatedItemType
//    case object Text extends RelatedItemType
//    case object Workflow extends RelatedItemType
//    case object Other extends RelatedItemType
//  }
//
//  /**
//   * Number type enumeration
//   */
//  sealed trait NumberType
//  object NumberType {
//    case object Article extends NumberType
//    case object Chapter extends NumberType
//    case object Edition extends NumberType
//    case object Episode extends NumberType
//    case object Issue extends NumberType
//    case object Other extends NumberType
//    case object Part extends NumberType
//    case object Report extends NumberType
//    case object Volume extends NumberType
//  }
}
