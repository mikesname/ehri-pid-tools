package services

import helpers.{AppSpec, DatabaseSupport}
import models.PidType

class SqlPidServiceSpec extends AppSpec with DatabaseSupport {

  private def pidService = inject[SqlPidService]

  "SqlPidService" should {
    "find all items of a given type" in {
      await(pidService.findAll(PidType.DOI)).length mustBe 1
    }

    "create new items" in {
      val newPid = await(pidService.create(PidType.DOI, "10.1234/5678", "https://foo.bar/baz", "system"))
      newPid.target mustBe "https://foo.bar/baz"
      newPid.value mustBe "10.1234/5678"
    }

    "error on creating existing items" in {
      val exception = intercept[Exception] {
        await(pidService.create(PidType.DOI, "10.14454/fxws-0523", "https://foo.bar/baz", "system"))
      }
      exception.getMessage must include("duplicate key value violates unique constraint")
    }

    "update existing items" in {
      val updatedPid = await(pidService.update(PidType.DOI, "10.14454/fxws-0523", "https://foo.bar/baz/updated"))
      updatedPid.target mustBe "https://foo.bar/baz/updated"
      updatedPid.value mustBe "10.14454/fxws-0523"
    }

    "delete items" in {
      await(pidService.delete(PidType.DOI, "10.14454/fxws-0523")) mustBe true
    }
  }
}
